package com.mycloset.mycloset.ui

import io.realm.DynamicRealm
import io.realm.RealmMigration

// db를 재정의해주는 class
class MyMigration: RealmMigration{
    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        if(oldVersion==0.toLong()) {
            var schema = realm!!.schema
            schema.create("RecordItem")
                    .addPrimaryKey("id")
                    .addField("date", String.javaClass)
                    .addField("time", Int.javaClass)
                    .addField("weather", String.javaClass)
                    .addField("temper", Int.javaClass)
                    .addField("feel", Int.javaClass)
                    .addField("outer", String.javaClass)
                    .addField("top", String.javaClass)
                    .addField("bottom", String.javaClass)
                    .addField("memo", String.javaClass)
        }
    }

}