package uz.aziz.textdisplay

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import uz.aziz.textdisplay.databinding.ActivitySecondBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            arraylistToPdf()
//            createandDisplayPdf("Azizxon ")
        }
    }

    fun arraylistToPdf() {
        try {
            val list = ArrayList<String>()
            list.add("Element 1")
            list.add("Element 2")
            list.add("Element 3")
            list.add("Element 4")
            list.add("Element 5")
            val document = Document(PageSize.A4)

            val path = Environment.getExternalStorageDirectory().absolutePath + "/Dir"
            val dir = File(path)
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, "newFile.pdf")
            val fOut = FileOutputStream(file)
            PdfWriter.getInstance(document, fOut)
//            val fileOutputStream = FileOutputStream(File("newFile.pdf")) as OutputStream
//            PdfWriter.getInstance(document, fileOutputStream)
//            PdfWriter.getInstance(document, fileOutputStream)
//            PdfWriter.getInstance(document, FileOutputStream(File("newFile.pdf")))
            document.open()

            val font = Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            for (element in list) {
                val paragraph = Paragraph(element, font)
                document.add(paragraph)
            }

            // PDF faylni yopish

            // PDF faylni yopish
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        viewPdf("newFile.pdf", "Dir")

    }

    fun createandDisplayPdf(text: String?) {
        val doc = Document()
        try {
            val path = Environment.getExternalStorageDirectory().absolutePath + "/Dir"
            val dir = File(path)
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, "newFile.pdf")
            val fOut = FileOutputStream(file)
            PdfWriter.getInstance(doc, fOut)

            //open the document
            doc.open()
            val p1 = Paragraph(text)
            val paraFont = Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            p1.alignment = Paragraph.ALIGN_CENTER
            p1.font = paraFont

            //add paragraph to document
            doc.add(p1)
        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } catch (e: IOException) {
            Log.e("PDFCreator", "ioException:$e")
        } finally {
            doc.close()
        }
        viewPdf("newFile.pdf", "Dir")
    }

    // Method for opening a pdf file
    private fun viewPdf(file: String, directory: String) {
        val pdfFile = File(
            Environment.getExternalStorageDirectory().toString() + "/" + directory + "/" + file
        )
        val path: Uri = Uri.fromFile(pdfFile)

        // Setting the intent for pdf reader
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(path, "application/pdf")
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        try {
            startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No application has been found to open PDF files.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}