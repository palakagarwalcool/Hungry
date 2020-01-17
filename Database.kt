package database;
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.ActionBarDrawerToggle
import com.Model.Order
import com.example.eatit.MainActivity
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import java.util.ArrayList

class Database(context: Context) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER) {
    companion object {
        private val DB_NAME: String = "EatItDb.db"
        private val DB_VER: Int = 1
    }



    fun getCarts(): ArrayList<Order> {
            val db = this.readableDatabase
            val bd = SQLiteQueryBuilder()
            val sqlSelect = arrayOf("ProductId", "ProductName", "Quantity", "Price", "Discount")
            val sqlTable = "orderDetail"
            bd.tables = sqlTable
            val c = bd.query(db, sqlSelect, null, null, null, null, null)
            val result = ArrayList<Order>()
            if (c.moveToFirst()) {
                do {
                    result.add(Order(c.getString(c.getColumnIndex("ProductId")), c.getString(c.getColumnIndex("ProductName")), c.getString(c.getColumnIndex("Quantity")), c.getString(c.getColumnIndex("Price")), c.getString(c.getColumnIndex("Discount"))))


                } while (c.moveToNext())
            }
            return result

        }

    fun addToCart(order: Order) {
        val db = this.readableDatabase
        val query = String.format("INSERT INTO ORDERDETAIL(ProductId,ProductName,Quantity,Price,Discount)VALUES('%s','%s','%s','%s','%s');",
                order.productId,
                order.productName,
                order.quantity,
                order.price,
                order.discount)
        db.execSQL(query)
    }

    fun cleanCart() {
        val db = this.readableDatabase
        val query = String.format("DELETE FROM ORDER DETAIL")
        db.execSQL(query)
    }




}
