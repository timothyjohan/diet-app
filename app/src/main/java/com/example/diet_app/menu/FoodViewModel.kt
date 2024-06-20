package com.example.diet_app.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class Post(val title: String, val content: String)

class FoodViewModel : ViewModel() {

    private val _foodList = MutableLiveData<List<ClassFood>>()
    val foodList: LiveData<List<ClassFood>> get() = _foodList

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        val dummyFoods = listOf(
            ClassFood("Apple", 52.0, 0.2, 0.3, 14.0),
            ClassFood("Banana", 89.0, 0.3, 1.1, 23.0),
            ClassFood("Carrot", 41.0, 0.2, 0.9, 10.0),
            ClassFood("Donut", 452.0, 25.0, 4.9, 51.0)
        )
        _foodList.value = dummyFoods
    }
}

//class FoodViewModel : ViewModel() {
//    private val _foodItems = MutableLiveData<List<ClassFood>>()
//    val foodItems: LiveData<List<ClassFood>> get() = _foodItems
//
//    private val _post = MutableLiveData<Post>()
//    val post: LiveData<Post> get() = _post
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> get() = _errorMessage
//
//    fun fetchFoodData(foodName: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val url = URL("https://trackapi.nutritionix.com/v2/natural/nutrients/")
//            val httpURLConnection = url.openConnection() as HttpURLConnection
//            httpURLConnection.requestMethod = "GET"
//            httpURLConnection.setRequestProperty("Content-Type", "application/json")
//            httpURLConnection.setRequestProperty("x-app-id", "d3b15cb2")
//            httpURLConnection.setRequestProperty("x-app-key", "359e69c63492b02889414941534a6f5e")
//
//            try {
//                val responseCode = httpURLConnection.responseCode
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    val response = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
//                    val jsonObject = JSONObject(response)
//                    val foodItem = ClassFood(
//                        foodName = jsonObject.getString("food_name"),
//                        calories = jsonObject.getDouble("calories"),
//                        fats = jsonObject.getDouble("fats"),
//                        protein = jsonObject.getDouble("protein"),
//                        carbs = jsonObject.getDouble("carbohydrate")
//                    )
//                    withContext(Dispatchers.Main) {
//                        _foodItems.value = listOf(foodItem)
//                    }
//                } else {
//                    val errorResponse = httpURLConnection.errorStream.bufferedReader().use { it.readText() }
//                    Log.e("PostViewModel", "Error fetching data: $errorResponse")
//                    withContext(Dispatchers.Main) {
//                        _errorMessage.value = "Error fetching data: $responseCode"
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("PostViewModel", "Exception fetching data", e)
//                withContext(Dispatchers.Main) {
//                    _errorMessage.value = "Exception fetching data: ${e.message}"
//                }
//            } finally {
//                httpURLConnection.disconnect()
//            }
//        }
//    }
//
//    fun setPost(title: String, content: String) {
//        _post.value = Post(title, content)
//    }
//}
