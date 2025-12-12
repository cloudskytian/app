package moe.crx.overport.patching

import com.reandroid.apk.ApkModule
import com.reandroid.apk.ApkModuleJsonDecoder
import com.reandroid.arsc.chunk.PackageBlock
import com.reandroid.json.JSONObject
import kotlinx.serialization.json.Json
import moe.crx.overport.config.OverportConfig
import java.io.File

class PatchExecutor(private val librariesDir: File, private val workingDir: File, private val apkModule: ApkModule) {
    companion object {
        private val json = Json { ignoreUnknownKeys = true }
    }

    fun getLibraries(): File {
        return librariesDir
    }

    fun applicationPackage(): String {
        return apkModule.androidManifest.packageName
    }

    fun selectWorkspace(block: FileTransformer.() -> Unit) {
        workingDir.run {
            if (exists()) {
                FileTransformer(this).block()
            }
        }
    }

    fun selectFile(fileName: String, block: FileTransformer.() -> Unit) {
        workingDir.resolve(fileName).run {
            if (exists()) {
                FileTransformer(this).block()
            }
        }
    }

    fun createFile(fileName: String, block: FileTransformer.() -> Unit) {
        workingDir.resolve(fileName).run {
            parentFile?.mkdirs()
            createNewFile()
            FileTransformer(this).block()
        }
    }

    fun selectResources(block: FileTransformer.() -> Unit) {
        selectFile("resources/resources.arsc.json", block)
    }

    fun selectManifest(block: FileTransformer.() -> Unit) {
        selectFile("AndroidManifest.xml.json", block)
    }

    fun selectResourcesJson(block: JSONObject?.() -> Unit) {
        selectResources {
            val json = readJson()
            block(json)
            json?.let { writeJson(it) }
        }
    }

    fun selectManifestJson(block: JSONObject?.() -> Unit) {
        selectManifest {
            val json = readJson()
            block(json)
            json?.let { writeJson(it) }
        }
    }

    fun selectPackageBlock(block: PackageBlock.() -> Unit) {
        apkModule.tableBlock.getPackages(apkModule.androidManifest.packageName).forEach {
            block(it)
        }
        ApkModuleJsonDecoder(apkModule).decodeResourceTable(workingDir)
    }

    fun selectConfig(block: OverportConfig.() -> Unit) {
        createLibrary("liboverport.config.so") {
            val config = runCatching {
                json.decodeFromString<OverportConfig>(readText())
            }.getOrElse {
                OverportConfig(version = 1)
            }

            block(config)

            val jsonString = json.encodeToString(config)
            writeText(jsonString)
        }
    }

    fun selectLibrary(fileName: String, block: FileTransformer.() -> Unit) {
        listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64").forEach { arch ->
            val archDirectory = workingDir.resolve("root/lib/$arch")
            if (archDirectory.exists()) {
                archDirectory.resolve(fileName).run {
                    if (exists()) {
                        FileTransformer(this).block()
                    }
                }
            }
        }
    }

    fun createLibrary(fileName: String, block: FileTransformer.() -> Unit) {
        listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64").forEach { arch ->
            val archDirectory = workingDir.resolve("root/lib/$arch")
            if (archDirectory.exists()) {
                archDirectory.resolve(fileName).run {
                    createNewFile()
                    FileTransformer(this).block()
                }
            }
        }
    }

    fun selectSmali(vararg fileNames: String, block: FileTransformer.() -> Unit) {
        workingDir.resolve("smali").listFiles()?.forEach { dir ->
            if (fileNames.isEmpty()) {
                dir.walk().forEach { file ->
                    if (file.isFile && file.name.endsWith(".smali")) {
                        FileTransformer(file).block()
                    }
                }
            } else {
                fileNames.forEach { fileName ->
                    dir.resolve("${fileName}.1.smali").run {
                        if (exists()) {
                            FileTransformer(this).block()
                        }
                    }
                    dir.resolve("${fileName}.smali").run {
                        if (exists()) {
                            FileTransformer(this).block()
                        }
                    }
                }
            }
        }
    }

    fun createSmali(fileName: String, block: FileTransformer.() -> Unit) {
        workingDir.resolve("smali").listFiles()?.forEach { dir ->
            dir.resolve("${fileName}.1.smali").run {
                if (exists()) {
                    FileTransformer(this).block()
                    return
                }
            }
            dir.resolve("${fileName}.smali").run {
                if (exists()) {
                    FileTransformer(this).block()
                    return
                }
            }
        }

        workingDir
            .resolve("smali")
            .listFiles()
            ?.reduceOrNull { left, right ->
                val leftValue = left.name.substringAfter("classes").toIntOrNull() ?: 0
                val rightValue = right.name.substringAfter("classes").toIntOrNull() ?: 0
                if (rightValue > leftValue) right else left
            }
            .let {
                it ?: workingDir.resolve("smali/classes").apply { mkdirs() }
            }
            .resolve("${fileName}.smali")
            .apply {
                parentFile?.mkdirs()
                createNewFile()
            }
            .run {
                if (exists()) {
                    FileTransformer(this).block()
                }
            }
    }
}
