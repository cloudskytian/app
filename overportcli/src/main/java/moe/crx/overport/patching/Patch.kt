package moe.crx.overport.patching

data class Patch(
    val name: String,
    val isRecommended: Boolean = true,
    val executor: PatchExecutor.(List<String>) -> Unit,
)
