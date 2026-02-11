/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author victoire
 */
public class NewClass {
    int b = 223;
    void terminate(){
        int i;
        for (i=1; i<=10; i++){
            
            if(i%2 == 0){                        
                continue;
            }
            System.out.println("This is  iteration number"+ i);
            
        }
    }
    void looping(){
        int i,j;
        for (i=1; i<=10; i++){
            for (j=1; j<=10; j++){
                System.out.println("The value for i is "+ i);
                System.out.println("The value for i is "+ i);
            }
        }
    }
}
