package com.o7services.colourworld;

import android.app.ActivityOptions;
import android.content.Intent;
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

public class ExtdetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView,deatil,name,price;
    String tid,ttype,tname,timage,tprice,tdescription,tspin;
    int tqnty;
    Spinner spin;
    String[] quantity={"1","2","3","4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extdetail);
        TextView textViewAddToCart = findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = findViewById(R.id.text_action_bottom2);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.title);
        deatil = findViewById(R.id.textView);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);

        Intent intent = getIntent();
        tid = intent.getStringExtra("id");
        ttype = intent.getStringExtra("type");
        tname = intent.getStringExtra("name");
        timage = intent.getStringExtra("photo");
        tprice = intent.getStringExtra("price");
        spin = findViewById(R.id.spin);
       // tspin = intent.getStringExtra("qty");
        tdescription = intent.getStringExtra("description");

        textView.setText(intent.getStringExtra("type"));
        name.setText("Item : "+intent.getStringExtra("name"));
        price.setText("Price : Rs."+intent.getStringExtra("price"));
        deatil.setText(intent.getStringExtra("description"));
        Picasso.with(this).load(intent.getStringExtra("photo")).into(imageView);

        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,quantity);
        spin.setAdapter(arrayAdapter);

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try{

                  for (int i=0;i<CartContent.CARTLIST.size();i++)
                  {
                      if (CartContent.CARTLIST.get(i).productId.equals(tid))
                      {
                          Log.e("ID",String.valueOf(Integer.parseInt(CartContent.CARTLIST.get(i).productPrice)*CartContent.CARTLIST.get(i).productqnty));
                          //HomeActivity.notificationCountCart--;
                          CartContent.TOTALAMOUNT = CartContent.TOTALAMOUNT-(Integer.parseInt(CartContent.CARTLIST.get(i).productPrice)*Integer.parseInt(CartContent.CARTLIST.get(i).quantity));
                          CartContent.CARTLIST.remove(i);
                          break;
                      }
                  }

                CartContent.ProductCart product = new CartContent.ProductCart(tid,ttype,tname,timage,tprice,tdescription,tqnty,spin.getSelectedItem().toString());
                CartContent.CARTLIST.add(product);
                Toast.makeText(ExtdetailActivity.this,"Item added to cart.",Toast.LENGTH_SHORT).show();
                HomeActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(HomeActivity.notificationCountCart);
                CartContent.TOTALAMOUNT += Integer.parseInt(tprice)*Integer.parseInt(spin.getSelectedItem().toString());
            }
            catch (Exception e)
            {
            e.printStackTrace();
            }
            }
        });
        final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this,android.R.anim.fade_in,android.R.anim.fade_out);
        final Intent intents = new Intent(ExtdetailActivity.this,HomeActivity.class);

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intents.putExtra("cart","cart");
                startActivity(intents,activityOptions.toBundle());
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}



