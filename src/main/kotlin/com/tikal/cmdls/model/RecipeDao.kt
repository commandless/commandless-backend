package com.tikal.cmdls.model

import io.reactivex.Flowable
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class RecipeDao {
    companion object {
        val TABLE_NAME = "RECIPE"
    }

    @Inject
    lateinit var client: PgPool

    private fun rowSetToRecipe(row: Row): Recipe =
            Recipe(row.getLong("id"), row.getString("description"), row.getLong("command_id"), row.getString("inputs")?:"Inputs", null)

    fun findRecipes(keys: List<String>): Flowable<Recipe> {
        var list = keys.map { "'$it'" }.joinToString(separator = ",")
        return client.rxQuery("""
            select id, command_id, description, inputs from recipe where id in(
                select distinct recipe_id from keyword_recipe where keyword_id  in(
                    select id from keyword where label in ($list)
                )
            )
        """.trimIndent())
                .flatMapPublisher { Flowable.fromIterable(it.asIterable()) }
                .map (::rowSetToRecipe)
    }





}