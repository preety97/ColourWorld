package com.o7services.colourworld;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.o7services.colourworld.customclass.CartContent;
import com.o7services.colourworld.notificationclass.NotificationCountSetClass;
import com.squareup.picasso.Picasso;

public class ProductdetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView,deatil,name,price;
    String tid,ttype,tname,timage,tprice,tdescription;
    int tqnty;
    FragmentManager fragmentManager;
    Spinner spinner;
    String[] quantity={"1","2","3","4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        TextView textViewAddToCart = findViewById(R.id.text_action_bottom1);
        TextView buy= findViewById(R.id.buy);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.title);
        deatil = findViewById(R.id.textView);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);

        spinner = findViewById(R.id.spin);

        Intent intent = getIntent();
        tid = intent.getStringExtra("id");
        ttype = intent.getStringExtra("type");
        tname = intent.getStringExtra("name");
        timage = intent.getStringExtra("photo");
        tprice = intent.getStringExtra("price");
        tdescription = intent.getStringExtra("description");

        textView.setText(intent.getStringExtra("type"));
        name.setText("Item : "+intent.getStringExtra("name"));
        price.setText("Price : Rs."+intent.getStringExtra("price"));
        deatil.setText(intent.getStringExtra("description"));
        //tqnty.setText(intent.getStringExtra("qnty"));
        Picasso.with(this).load(intent.getStringExtra("photo")).into(imageView);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,quantity);
        spinner.setAdapter(arrayAdapter);

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<CartContent.CARTLIST.size();i++)
                {
                    if (CartContent.CARTLIST.get(i).productId.equals(tid))
                    {
                        Log.e("ID",String.valueOf(Integer.parseInt(CartContent.CARTLIST.get(i).productPrice)*Integer.parseInt(CartContent.CARTLIST.get(i).quantity)));
                        HomeActivity.notificationCountCart--;
                        CartContent.TOTALAMOUNT = CartContent.TOTALAMOUNT-(Integer.parseInt(CartContent.CARTLIST.get(i).productPrice)*Integer.parseInt(CartContent.CARTLIST.get(i).quantity));
                        CartContent.CARTLIST.remove(i);
                        break;
                    }
                }

                CartContent.ProductCart product = new CartContent.ProductCart(tid,ttype,tname,timage,tprice,tdescription,tqnty,spinner.getSelectedItem().toString());
                CartContent.CARTLIST.add(product);
                Toast.makeText(ProductdetailActivity.this,"Item added to cart.",Toast.LENGTH_SHORT).show();
                //fragmentManager.beginTransaction().replace(R.id.content,new CartFragment()).commit();
                HomeActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(HomeActivity.notificationCountCart);
                CartContent.TOTALAMOUNT += Integer.parseInt(tprice)*Integer.parseInt(spinner.getSelectedItem().toString());
            }
        });
        final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this,android.R.anim.fade_in,android.R.anim.fade_out);
        final Intent intents = new Intent(ProductdetailActivity.this,HomeActivity.class);

        //startActivity(intents);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentManager.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intents.putExtra("cart","cart");
                startActivity(intents,activityOptions.toBundle());
                finish();
            }
        });}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}



