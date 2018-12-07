package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);

    }

    public void createExpression(int number) {
        //напишите тут ваш код
        int[] arr={1, 3, 9, 27, 81, 243, 729, 2187};
        String s="";
        //int nn=number;
        while (number>0)
        {
            if (number%3==0)
            {
                s=s+"0";
                number/=3;
            }
            else if (number%3==1)
            {
                s=s+"+";
                number/=3;
            }
            else if (number%3==2)
            {
                s=s+"-";
                number/=3;
                number++;
            }
        }
        int sum=0;
        String str="";
        for (int i=0;i<s.length(); i++)
        {
            if (s.charAt(i)=='+') {
                str+= " + " + arr[i];
                sum+=arr[i];
            }
            else if (s.charAt(i)=='-')
            {
                str+= " - " + arr[i];
                sum -= arr[i];
            }
        }
        System.out.println(sum + " =" + str);
    }
}