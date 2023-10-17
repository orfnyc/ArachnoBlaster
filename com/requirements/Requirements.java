package com.requirements;

import java.util.ArrayList;

public class Requirements
{
    public static void meetRequirements()
    {
        int n = (int)(Math.random() * 10) + 2;
        System.out.println("The " + n + "th term of the fibonacci sequence is " + fibonacci(n));
        traverseString("null");
        System.out.println();
        System.out.println(traverseArray(new int[]{1, 2, 3, 4}, 0));
        ArrayList<Integer> arr = new ArrayList<>(); arr.add(1); arr.add(2); arr.add(3); arr.add(5);
        System.out.println(traverseArrayList(arr, 0));
        arr = new ArrayList<>(); for(int i = 0; i < 10; i++) {arr.add((int)(Math.random()*10));}
        int[] arr2 = new int[10]; for(int i = 0; i < 10; i++) {arr2[i] = (int)(Math.random()*10);}
        for(int i : arr2) {System.out.print(i + " ");}
        mergeSort(arr2);
        System.out.println();
        for(int i : arr2) {System.out.print(i + " ");}
        System.out.println("\n----------------");
        for(int i : arr) System.out.print(i + " ");
        mergeSort(arr);
        System.out.println();
        for(int i : arr) System.out.print(i + " ");
        int i = (int)(Math.random() * 10);
        System.out.println("\nTarget: " + i + " Index: " + binarySearch(arr2, i));
    }

    public static int fibonacci(int n)
    {
        if(n == 1) return 1;
        if(n == 0) return 0;
        return fibonacci(n-1) + fibonacci(n-2);
    }

    public static void traverseString(String str)
    {
        if(str.length() > 0) 
        {
            System.out.print(str.substring(0, 1)); traverseString(str.substring(1));
        }
    }  

    public static int traverseArray(int[] arr, int n)
    {
        if(n >= arr.length) return 0;
        return arr[n] + traverseArray(arr, n+1);
    }

    public static int traverseArrayList(ArrayList<Integer> arr, int n)
    {
        if(n >= arr.size()) return 0;
        return arr.get(n) + traverseArrayList(arr, n+1);
    }

    public static int binarySearch(int[] arr, int target)
    {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right)
        {
        int middle = (left + right) / 2;
        if (target < arr[middle])
        {
            right = middle - 1;
        }
        else if (target > arr[middle])
        {
            left = middle + 1;
        }
        else return middle;
        }
        return -1;
    }

    public static void mergeSort(int[] arr)
    {
        int[] temp = new int[arr.length];
        mergeSortHelper(arr, 0, arr.length - 1, temp);
    }
  
    private static void mergeSortHelper(int[] arr, int from, int to, int[] temp)
    {
        if (from < to)
        {
        int middle = (from + to) / 2;
        mergeSortHelper(arr, from, middle, temp);
        mergeSortHelper(arr, middle + 1, to, temp);
        merge(arr, from, middle, to, temp);
        }
    }
  
    private static void merge(int[] arr, int from, int mid, int to, int[] temp)
    {
        int i = from;
        int j = mid + 1;
        int k = from;
        
        while (i <= mid && j <= to)
        {
            if (arr[i] < arr[j])
            {
                temp[k] = arr[i];
                i++;
            }
            else
            {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }
        
        while (i <= mid)
        {
            temp[k] = arr[i];
            i++;
            k++;
        }
        
        while (j <= to)
        {
            temp[k] = arr[j];
            j++;
            k++;
        }
        for (k = from; k <= to; k++)
        {
            arr[k] = temp[k];
        }
    }

    public static void mergeSort(ArrayList<Integer> arr)
    {
        int[] arr2 = new int[arr.size()];
        for(int i = 0; i < arr.size(); i++) arr2[i] = arr.get(i);
        int[] temp = new int[arr.size()];
        mergeSortHelper(arr2, 0, arr2.length - 1, temp);
        for(int i = 0; i < arr.size(); i++) arr.set(i, arr2[i]);
    }
  

}
