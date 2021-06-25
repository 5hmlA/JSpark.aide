package jzy.taining.plugins.jspark.features.templates.wizard

import com.android.SdkConstants
import com.android.tools.idea.npw.project.getPackageForApplication
import com.google.common.collect.Iterables
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.XmlElementFactory
import com.intellij.psi.XmlRecursiveElementWalkingVisitor
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import jzy.taining.plugins.jspark.features.templates.data.Environment
import jzy.taining.plugins.jspark.log.EventLogger
import org.jetbrains.android.AndroidFileTemplateProvider
import org.jetbrains.android.dom.manifest.Manifest
import org.jetbrains.android.dom.manifest.getPrimaryManifestXml
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.SourceProviderManager


class AndroidMainfestMagic(envi: Environment) {
    val facet = AndroidFacet.getInstance(envi.psiDirectory)!!
    val project = envi.project

    fun addActivitysTag(vararg actFullName: String) {
        EventLogger.log("== addActivitysTag == $actFullName")
        WriteCommandAction.runWriteCommandAction(project) {
            val mutableListOf = mutableListOf<XmlTag>()
            for (name in actFullName) {
                mutableListOf.add(
                    XmlElementFactory.getInstance(project)
                        .createTagFromText(androidActivityTag(name))
                )
            }
            addActivityTag(mutableListOf)
        }
    }

    fun addActivityTag(actTagXml: MutableList<XmlTag>) {
        val manifest = getOrCreateTargetManifestFile(facet) as XmlFile?
        EventLogger.log("$ addActivityTag start $manifest")
        manifest?.acceptChildren(object : XmlRecursiveElementWalkingVisitor() {
            override fun visitXmlTag(tag: XmlTag) {
                if (SdkConstants.TAG_MANIFEST == tag.name) {
                    var applicationTag: XmlTag? = null
                    for (child in tag.children) {
//                        if (child is XmlTag) {
//                            child.children.forEach { if(it is XmlTag) println(it.name) }
//                        }
                        if (child is XmlTag && SdkConstants.TAG_APPLICATION == (child).name) {
                            applicationTag = child
                            actTagXml.forEach { applicationTag.addSubTag(it, false) }
//                            for (xmlTag in actTagXml) {
//                                applicationTag.addSubTag(xmlTag, false)
//                            }
                            CodeStyleManager.getInstance(project).reformat(manifest)
                        }
                    }
                } else {
                    super.visitXmlTag(tag)
                }
            }
        })
    }

    private fun getOrCreateTargetManifestFile(facet: AndroidFacet): PsiFile? {
        val mainfest = facet.getPrimaryManifestXml()
//        val mainManifest = Manifest.getMainManifest(facet)
        EventLogger.log("== getOrCreateTargetManifestFile == $mainfest")
        if (mainfest != null) {
            return mainfest;
        }
        val manager = PsiManager.getInstance(project)
        val manifestFileUrl =
            Iterables.getFirst(SourceProviderManager.getInstance(facet).sources.manifestFileUrls, null)
        if (manifestFileUrl != null) {
            val manifestFile = VirtualFileManager.getInstance().findFileByUrl(manifestFileUrl)
            if (manifestFile != null) {
                return manager.findFile(manifestFile)
            } else {
                val parentDir = VfsUtil.getParentDir(manifestFileUrl)
                if (parentDir != null) {
                    val directory = VirtualFileManager.getInstance().findFileByUrl(parentDir)
                    if (directory != null) {
                        val targetDirectory = manager.findDirectory(directory)
                        if (targetDirectory != null) {
                            try {
                                return AndroidFileTemplateProvider
                                    .createFromTemplate(
                                        AndroidFileTemplateProvider.ANDROID_MANIFEST_TEMPLATE,
                                        SdkConstants.FN_ANDROID_MANIFEST_XML,
                                        targetDirectory
                                    ) as PsiFile
                            } catch (ex: Exception) {
                                println(ex.stackTrace)
                            }
                        }
                    }
                }
            }
        }
        println("Couldn't determine manifest file for module $facet")
        return null
    }

    fun androidActivityTag(activityFullName: String) =
        """
        <!--generated by jspark-->
        <activity android:name="$activityFullName"
                  android:launchMode="singleTop"
                  android:exported="false"/>
        """
}


