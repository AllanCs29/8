
package com.example.routineapp.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

private fun prefs(ctx: Context) = ctx.getSharedPreferences("routine_prefs", Context.MODE_PRIVATE)

fun saveItems(ctx: Context, items: List<RoutineItem>) {
    val arr = JSONArray()
    items.forEach {
        val o = JSONObject()
        o.put("t", it.title); o.put("h", it.time ?: JSONObject.NULL); o.put("d", it.done)
        arr.put(o)
    }
    prefs(ctx).edit().putString("items", arr.toString()).apply()
}

fun loadItems(ctx: Context): List<RoutineItem> {
    val s = prefs(ctx).getString("items", null) ?: return emptyList()
    return try {
        val arr = JSONArray(s)
        List(arr.length()) { i ->
            val o = arr.getJSONObject(i)
            RoutineItem(
                o.getString("t"),
                if (o.isNull("h")) null else o.getString("h"),
                o.optBoolean("d", false)
            )
        }
    } catch (_: Exception) { emptyList() }
}

fun markToday(ctx: Context) {
    prefs(ctx).edit().putString("last_day", LocalDate.now().toString()).apply()
}
fun isNewDay(ctx: Context): Boolean {
    val last = prefs(ctx).getString("last_day", "") ?: ""
    return last != LocalDate.now().toString()
}

fun appendHistory(ctx: Context, done: Int, total: Int) {
    val arr = JSONArray(prefs(ctx).getString("hist","[]"))
    val o = JSONObject(); o.put("d", done); o.put("t", total)
    arr.put(o)
    val trimmed = JSONArray()
    val start = kotlin.math.max(0, arr.length()-30)
    for (i in start until arr.length()) trimmed.put(arr.getJSONObject(i))
    prefs(ctx).edit().putString("hist", trimmed.toString()).apply()
}
fun loadHistory(ctx: Context): List<DayHistory> {
    val s = prefs(ctx).getString("hist","[]") ?: "[]"
    return try {
        val arr = JSONArray(s)
        List(arr.length()) { i ->
            val o = arr.getJSONObject(i)
            DayHistory(o.getInt("d"), o.getInt("t"))
        }
    } catch (_: Exception) { emptyList() }
}

fun saveExercises(ctx: Context, ex: List<Exercise>) {
    val arr = JSONArray()
    ex.forEach {
        val o = JSONObject()
        o.put("n", it.name); o.put("s", it.sets); o.put("r", it.reps); o.put("ds", it.doneSets)
        arr.put(o)
    }
    prefs(ctx).edit().putString("ex", arr.toString()).apply()
}

fun loadExercises(ctx: Context): List<Exercise> {
    val s = prefs(ctx).getString("ex", null) ?: return emptyList()
    return try {
        val arr = JSONArray(s)
        List(arr.length()) { i ->
            val o = arr.getJSONObject(i)
            Exercise(o.getString("n"), o.getInt("s"), o.getInt("r"), o.optInt("ds",0))
        }
    } catch (_: Exception) { emptyList() }
}
