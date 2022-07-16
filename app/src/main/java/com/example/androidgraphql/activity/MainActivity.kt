package com.example.androidgraphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.androidgraphql.DeleteUserMutation
import com.example.androidgraphql.InsertUserMutation
import com.example.androidgraphql.UpdateUserMutation
import com.example.androidgraphql.UsersListQuery
import com.example.androidgraphql.databinding.ActivityMainBinding
import com.example.androidgraphql.network.GraphQL
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        getUserList()
        updateUser("1","blablabla","rocket","twitter")
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {

                GraphQL.get().query(UsersListQuery(10)).execute()
            }catch (e: ApolloException) {
                Log.d("MainActivity2", e.toString())
                return@launchWhenResumed
            }
            val users = response.data?.users
            Log.d("MainActivity", users!!.toString())
        }
    }

    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(
                    InsertUserMutation(name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(
                    UpdateUserMutation(id, name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("UpdateUser", e.toString())
                return@launchWhenResumed
            }
            Log.d("UpdateUser", result.toString())
        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }
}