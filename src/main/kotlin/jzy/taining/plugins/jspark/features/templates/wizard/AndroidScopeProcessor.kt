package jzy.taining.plugins.jspark.features.templates.wizard

import com.android.tools.idea.model.AndroidModel
import com.android.tools.idea.model.queryPackageNameFromManifestIndex
import com.android.tools.idea.npw.project.getPackageForPath
import com.android.tools.idea.res.ResourceFolderRegistry
import com.google.common.collect.Iterables
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.ReadonlyStatusHandler
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.xml.XmlFile
import jzy.taining.plugins.jspark.features.templates.data.Environment
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.AndroidRootUtil
import org.jetbrains.android.facet.ResourceFolderManager
import org.jetbrains.android.facet.SourceProviderManager
import java.util.*


class AndroidScopeProcessor(val envi: Environment) {

    var facet: AndroidFacet = AndroidFacet.getInstance(envi.psiDirectory)!!

    val applicationid: String by lazy(LazyThreadSafetyMode.NONE) {
//        AndroidModel.get(facet)?.applicationId ?: "spark"
        facet.queryPackageNameFromManifestIndex()!!
    }
//        fun getApplicationId() = AndroidModel.get(facet)!!.applicationId

    fun performRefactoring(psiElement: PsiElement) {
        // We know this has to be an Android module
        val facet: AndroidFacet = AndroidFacet.getInstance(psiElement)!!
        val javaSourceFolders = SourceProviderManager.getInstance(facet!!).sources.javaDirectories
        val javaTargetDir = Iterables.getFirst(javaSourceFolders, null)
        val resDir = ResourceFolderManager.getInstance(facet!!).folders[0]
        val repo = ResourceFolderRegistry.getInstance(envi.project)[facet!!, resDir]
//        println(facet.getPackageForApplication())
        val touchedXmlFiles: Set<XmlFile> = HashSet()

        println(AndroidModel.get(facet)!!.applicationId)

        val defaultProperties = FileTemplateManager.getInstance(envi.project).defaultProperties
        val properties = Properties(defaultProperties)
        for ((key, value) in properties.entries) {
            println("$key === $value")
        }

        println(JavaDirectoryService.getInstance().getPackage(psiElement as PsiDirectory))

//        val facet: AndroidFacet = FacetManager.getInstance(module).getFacetsByType(AndroidFacet.ID)
        val manifestFile: VirtualFile = AndroidRootUtil.getPrimaryManifestFile(facet)!!
        !ReadonlyStatusHandler.ensureFilesWritable(facet.module.project, manifestFile)
//        val manifest: Manifest = AndroidUtils.loadDomElement(facet.module, manifestFile, Manifest::class.java)
    }

    fun findLayoutDir() =
        PsiManager.getInstance(envi.project).findDirectory(ResourceFolderManager.getInstance(facet).folders[0])

    fun getPackageName(subPackage: String?) = run {
        val project: Project = envi.project
        val projectFileIndex = ProjectRootManager.getInstance(project).fileIndex
        val virtualFile: VirtualFile = envi.psiDirectory.virtualFile
        if (subPackage.isNullOrEmpty()) {
            projectFileIndex.getPackageNameByDirectory(virtualFile)
        } else {
            "${projectFileIndex.getPackageNameByDirectory(virtualFile)}.$subPackage"
        }
    }

}