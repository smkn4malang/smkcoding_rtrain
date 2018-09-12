package com.example.robet.rtrain.support;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.robet.rtrain.R;

public class Config {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PrivateMode = 0;

    private static final String PrefName = "intro_slider-welcome";
    private static final String Flaunch = "IsFlaunch";

    public Config(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PrefName, PrivateMode);
        editor = pref.edit();
    }

    public void setFlaunch(boolean IsFtime) {
        editor.putBoolean(Flaunch, IsFtime);
        editor.commit();
    }

    public boolean IsFlaunch() {
        return pref.getBoolean(Flaunch, true);
    }

    public boolean getInfo(String info) {
        return pref.getBoolean(info, false);
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getName() {
        return pref.getString("name", "nothing found");
    }

    public String getEmail() {
        return pref.getString("email", "nothing found");
    }

    public void setInfo(String name, boolean info) {
        editor.putBoolean(name, info);
        editor.commit();
    }

    public void setCredit(int credit) {
        editor.putInt("credit", credit);
        editor.commit();
    }

    public int getCredit() {
        return pref.getInt("credit", 0);
    }

    public String getUsername() {
        return pref.getString("username", "");
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    public int getId(){
        return pref.getInt("id", 0);
    }

    public void setId(int id){
        editor.putInt("id", id);
        editor.commit();
    }

    public void setPassword(String password){
        editor.putString("password", password);
        editor.commit();
    }

    public String getPassword(){
        return pref.getString("password", "");
    }

    public void setCity(String[] city){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < city.length; i++){
            stringBuilder.append(city[i]).append(",");
        }
        editor.putString("city", stringBuilder.toString());
        editor.commit();
    }

    public String[] getCity(){
        String data = pref.getString("city", "nothing");
        String[] city = data.split(",");
        return  city;
    }

    public void setTime(String[] city){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < city.length; i++){
            stringBuilder.append(city[i]).append(",");
        }
        editor.putString("time", stringBuilder.toString());
        editor.commit();
    }

    public String[] getTime(){
        String data = pref.getString("time", "nothing");
        String[] time = data.split(",");
        return time;
    }

    public void setCart(String[] cart){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < cart.length; i++){
            stringBuilder.append(cart[i]).append(",");
        }
        editor.putString("cart", stringBuilder.toString());
        editor.commit();
    }

    public String[] getCart(){
        String mCart = pref.getString("cart", "nothing");
        String[] cart = mCart.split(",");
        return cart;
    }

    public void setStatus(boolean status){
        editor.putBoolean("status", status);
        editor.commit();
    }

    public boolean getStatus(){
        return pref.getBoolean("status", false);
    }

    public void setTheme(int resource){
        editor.putInt("resource", resource);
        editor.commit();
    }

    public int getTheme(){
        return pref.getInt("resource", 0);
    }

    public void setFont(String font){
        editor.putString("font", font);
        editor.commit();
    }

    public String getFont(){
        return pref.getString("font", "Default");
    }

}
