
package com.example.routineapp.util

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.routineapp.data.DayHistory
import com.example.routineapp.data.RoutineItem
import java.io.File

object PdfExporter {
    fun exportToday(ctx: Context, items: List<RoutineItem>) {
        exportText(ctx, "Hoy", items.joinToString("\n") { "${it.time ?: "--"}  ${it.title}  [${if (it.done) "✓" else " " }]" })
    }
    fun exportWeekly(ctx: Context, hist: List<DayHistory>) {
        exportText(ctx, "Semana", hist.joinToString("\n") { "${it.done}/${it.total}" })
    }
    private fun exportText(ctx: Context, title: String, body: String) {
        try {
            val doc = PdfDocument()
            val page = doc.startPage(PdfDocument.PageInfo.Builder(595,842,1).create())
            val canvas = page.canvas
            val p = Paint().apply { textSize = 16f }
            canvas.drawText("RoutineApp — " + title, 40f, 40f, p)
            val lines = body.split("\n")
            var y = 70f
            for (ln in lines) { canvas.drawText(ln, 40f, y, p); y += 20f }
            doc.finishPage(page)
            val dir = ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: ctx.filesDir
            val file = File(dir, "routine_" + title + ".pdf")
            doc.writeTo(file.outputStream())
            doc.close()
        } catch (_: Exception) { }
    }
}
