package com.vti.messageapp.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vti.messageapp.data.datasources.local.AppDatabase.Companion.DATABASE_VERSION
import com.vti.messageapp.data.datasources.local.converters.MessageStatusConverters
import com.vti.messageapp.data.datasources.local.converters.UserStatusConverter
import com.vti.messageapp.data.datasources.local.dao.ConversationDao
import com.vti.messageapp.data.datasources.local.dao.MessageDao
import com.vti.messageapp.data.datasources.local.dao.ParticipantDao
import com.vti.messageapp.data.datasources.local.dao.UserDao
import com.vti.messageapp.data.datasources.local.entities.ConversationEntity
import com.vti.messageapp.data.datasources.local.entities.MessageEntity
import com.vti.messageapp.data.datasources.local.entities.ParticipantEntity
import com.vti.messageapp.data.datasources.local.entities.UserEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        MessageEntity::class,
        ConversationEntity::class,
        ParticipantEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(MessageStatusConverters::class, UserStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "message_app.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(@ApplicationContext context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        fun clearDatabase(context: Context) {

            INSTANCE?.clearAllTables()
            INSTANCE = buildDatabase(context).also { INSTANCE = it }
            val handler = CoroutineExceptionHandler { _, exception ->
                println("Caught during database creation --> $exception")
            }
            CoroutineScope(Dispatchers.IO).launch(handler) {
                prePopulateAppDatabase(
                    getInstance(context).userDao(),
                )
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(object : Callback() {
                    // Pre-populate the database after onCreate has been called. If you want to prepopulate at opening time then override onOpen function instead of onCreate.
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Do database operations through coroutine or any background thread
                        val handler = CoroutineExceptionHandler { _, exception ->
                            println("Caught during database creation --> $exception")
                        }

                        CoroutineScope(Dispatchers.IO).launch(handler) {
                            prePopulateAppDatabase(
                                getInstance(context).userDao(),
                            )
                        }
                    }
                })
                .build()

        fun prePopulateAppDatabase(userDao: UserDao) {
            userDao.insertAll(
                UserEntity(name = "909196671061", phone = "909196671061"),
                UserEntity(name = "909824830353", phone = "909824830353"),
                UserEntity(name = "909810565026", phone = "909810565026"),
                UserEntity(name = "909726822209", phone = "909726822209"),
                UserEntity(name = "909673129072", phone = "909673129072"),
                UserEntity(name = "909352982193", phone = "909352982193"),
                UserEntity(name = "909394105461", phone = "909394105461"),
                UserEntity(name = "909785876597", phone = "909785876597"),
                UserEntity(name = "909769646054", phone = "909769646054"),
                UserEntity(name = "909230524267", phone = "909230524267"),
                UserEntity(name = "909725319593", phone = "909725319593"),
                UserEntity(name = "909293654285", phone = "909293654285"),
                UserEntity(name = "909609412760", phone = "909609412760"),
                UserEntity(name = "909314268942", phone = "909314268942"),
                UserEntity(name = "909369272283", phone = "909369272283"),
                UserEntity(name = "909369862943", phone = "909369862943"),
                UserEntity(name = "909171221894", phone = "909171221894"),
                UserEntity(name = "908517758688", phone = "908517758688"),
                UserEntity(name = "909128920474", phone = "909128920474"),
                UserEntity(name = "908915696685", phone = "908915696685"),
                UserEntity(name = "908596317889", phone = "908596317889"),
                UserEntity(name = "908731889233", phone = "908731889233"),
                UserEntity(name = "909007204071", phone = "909007204071"),
                UserEntity(name = "909031808099", phone = "909031808099"),
                UserEntity(name = "906403076396", phone = "906403076396"),
                UserEntity(name = "905738049886", phone = "905738049886"),
                UserEntity(name = "906033669429", phone = "906033669429"),
                UserEntity(name = "905938787264", phone = "905938787264"),
                UserEntity(name = "905886991498", phone = "905886991498"),
                UserEntity(name = "906312887996", phone = "906312887996"),
                UserEntity(name = "906281728872", phone = "906281728872"),
                UserEntity(name = "905842804256", phone = "905842804256"),
                UserEntity(name = "905869839941", phone = "905869839941"),
                UserEntity(name = "909892757447", phone = "909892757447"),
                UserEntity(name = "909947868685", phone = "909947868685"),
                UserEntity(name = "907523483700", phone = "907523483700"),
                UserEntity(name = "907509873405", phone = "907509873405"),
                UserEntity(name = "907562599010", phone = "907562599010"),
                UserEntity(name = "907447103056", phone = "907447103056"),
                UserEntity(name = "907626551982", phone = "907626551982"),
                UserEntity(name = "907809995138", phone = "907809995138"),
                UserEntity(name = "907649420655", phone = "907649420655"),
                UserEntity(name = "907363704473", phone = "907363704473"),
                UserEntity(name = "907232609234", phone = "907232609234"),
                UserEntity(name = "907250692258", phone = "907250692258"),
                UserEntity(name = "908387443937", phone = "908387443937"),
                UserEntity(name = "907877678397", phone = "907877678397"),
                UserEntity(name = "908380020814", phone = "908380020814"),
                UserEntity(name = "908366828925", phone = "908366828925"),
                UserEntity(name = "908356302264", phone = "908356302264"),
                UserEntity(name = "907923461208", phone = "907923461208"),
                UserEntity(name = "908175316903", phone = "908175316903"),
                UserEntity(name = "906802776716", phone = "906802776716"),
                UserEntity(name = "906900981361", phone = "906900981361"),
                UserEntity(name = "906761879311", phone = "906761879311"),
                UserEntity(name = "907094014511", phone = "907094014511"),
                UserEntity(name = "906952330362", phone = "906952330362"),
                UserEntity(name = "906988081670", phone = "906988081670"),
                UserEntity(name = "907141157571", phone = "907141157571"),
                UserEntity(name = "906591856200", phone = "906591856200"),
                UserEntity(name = "907043284897", phone = "907043284897"),
                UserEntity(name = "906491180932", phone = "906491180932"),
                UserEntity(name = "903780109354", phone = "903780109354"),
                UserEntity(name = "903767746652", phone = "903767746652"),
                UserEntity(name = "903640742591", phone = "903640742591"),
                UserEntity(name = "904647437213", phone = "904647437213"),
                UserEntity(name = "904196911237", phone = "904196911237"),
                UserEntity(name = "904813481725", phone = "904813481725"),
                UserEntity(name = "904792375256", phone = "904792375256"),
                UserEntity(name = "904314828329", phone = "904314828329"),
                UserEntity(name = "904547704594", phone = "904547704594"),
                UserEntity(name = "904752272635", phone = "904752272635"),
                UserEntity(name = "904978677931", phone = "904978677931"),
                UserEntity(name = "905606438305", phone = "905606438305"),
                UserEntity(name = "902758153655", phone = "902758153655"),
                UserEntity(name = "902733514864", phone = "902733514864"),
                UserEntity(name = "902564703770", phone = "902564703770"),
                UserEntity(name = "902324815571", phone = "902324815571"),
                UserEntity(name = "901326399077", phone = "901326399077"),
                UserEntity(name = "901312626700", phone = "901312626700"),
                UserEntity(name = "901310215937", phone = "901310215937"),
                UserEntity(name = "900720666839", phone = "900720666839"),
                UserEntity(name = "900721344125", phone = "900721344125"),
                UserEntity(name = "901219920188", phone = "901219920188"),
                UserEntity(name = "901070959167", phone = "901070959167"),
                UserEntity(name = "903452548591", phone = "903452548591"),
                UserEntity(name = "903245522354", phone = "903245522354"),
                UserEntity(name = "902790276039", phone = "902790276039"),
                UserEntity(name = "903218581032", phone = "903218581032"),
                UserEntity(name = "902887931952", phone = "902887931952"),
                UserEntity(name = "903375239174", phone = "903375239174"),
                UserEntity(name = "903364838108", phone = "903364838108"),
                UserEntity(name = "903349386025", phone = "903349386025"),
                UserEntity(name = "902053916023", phone = "902053916023"),
                UserEntity(name = "901788743325", phone = "901788743325"),
                UserEntity(name = "901438371646", phone = "901438371646"),
                UserEntity(name = "901811552798", phone = "901811552798"),
                UserEntity(name = "901694758207", phone = "901694758207"),
                UserEntity(name = "901531140461", phone = "901531140461"),
                UserEntity(name = "901678020138", phone = "901678020138"),
            )
        }

    }

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun conversationDao(): ConversationDao
    abstract fun participantDao(): ParticipantDao
}