package com.oceanbrasil.oceanretrofitapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener {
            pesquisarLinguagem()
        }

        Glide.with(this) //1
                .load("https://i1.wp.com/deusanyjunior.dj/wp-content/uploads/dj-icon-55550d61v1_site_icon.png?fit=512%2C512")
                .transform(CircleCrop())
                .into(imageView)

    }

    private fun pesquisarLinguagem() {
        val linguagem = etLinguagem.text.toString()

        if (linguagem.isBlank()) {
            textView.text = ""
            etLinguagem.error = "Digite uma linguagem"
            return
        }

        textView.text = "Carregando resultados.."

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create( GitHubService::class.java )
        val call = service.searchRepositories("language:$linguagem")

        call.enqueue(object : Callback<GitHubRepositoriesResult> {

            override fun onFailure(
                    call: Call<GitHubRepositoriesResult>,
                    t: Throwable) {

                textView.text = "Falha ao realizar busca!"
            }

            override fun onResponse(
                    call: Call<GitHubRepositoriesResult>,
                    response: Response<GitHubRepositoriesResult> )
            {
                if (response.isSuccessful) {

                    val body = response.body()

                    body?.items?.let {
                        repositories -> textView.text = ""

                        repositories.forEach {
                            textView.append(it.name + " - " + it.owner.login + ", \n")

                        }
                    }
                }
            }
        })
    }
}