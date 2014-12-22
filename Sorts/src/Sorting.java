import java.util.Random;

/**
  * Sorting implementation
  * CS 1332 : Fall 2014
  * @author Bhavika Devnani
  * @version 1.0
  */
public class Sorting implements SortingInterface {

    // Do not add any instance variables.

    @Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        boolean swapped = true;
        int j = 0;
        T tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }

    /**
     * Swaps the elements at the two given indices.
     *
     * @param i  first index to be swapped.
     * @param j  second index to be swapped.
     * @param arr  array to be modified.
     */
    private <T> void swap(int i, int j, T[] arr) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0 && arr[i].compareTo(arr[j]) < 0) {
                j--;
            }
            insert(j + 1, i, arr);
        }
    }

    /**
     * Inserts the element at b into the position of a and shifts
     * the other elements one space to the right.
     *
     * @param a  index to be inserted at.
     * @param b  index of element to be inserted.
     * @param arr  array to be modified.
     */
    private <T extends Comparable<? super T>> void
    insert(int a, int b, T[] arr) {
        T toInsert = arr[b];
        for (int i = b; i > a; i--) {
            arr[i] = arr[i - 1];
        }
        arr[a] = toInsert;
    }

    @Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        int i, j, minIndex;
        int n = arr.length;
        for (i = 0; i < n - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (arr[j].compareTo(arr[minIndex]) < 0)
                        minIndex = j;
            if (minIndex != i) {
                T temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    @Override
    public <T extends Comparable<? super T>> void quicksort(T[] arr, Random r) {
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        quicksortHelper(arr, r, 0, arr.length - 1);
    }

    private <T extends Comparable<? super T>> void
    quicksortHelper(T[] arr, Random r, int a, int b) {
        if (b - a == 1) {
            if (arr[b].compareTo(arr[a]) < 0) {
                swap(a, b, arr);
            }
        } else if (a < b) {
            int begin = a;
            int end = b;
            int pivotIndex = r.nextInt(b - a + 1) + a;
            swap(pivotIndex, b, arr);
            b--;
            while (b > a) {
                if (arr[a].compareTo(arr[end]) < 0) {
                    a++;
                } else if (arr[b].compareTo(arr[end]) > 0) {
                    b--;
                } else {
                    swap(a, b, arr);
                    a++;
                }
            }
            if (arr[a].compareTo(arr[end]) < 0) {
                a++;
            }
            swap(end, a, arr);
            quicksortHelper(arr, r, begin, a - 1);
            quicksortHelper(arr, r, a + 1, end);
        }
    }
    @Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        T[] newArr = mergesortHelper(arr);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = newArr[i];
        }
//      System.out.println("arr");
//        for (int i = 0 ; i < arr.length; i++) {
//          System.out.println(arr[i]);
//        }
    }

    /**
     * Recursively sorts the given array using the merge sort algorithm.
     *
     * @param arr  array to be sorted with merge sort.
     * @return sorted array.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<? super T>> T[] mergesortHelper(T[] arr) {
        if (arr.length <= 1) {
            return arr;
        } else {
            int middle = arr.length / 2;
            T[] first = (T[]) new Comparable[middle];
            for (int i = 0; i < first.length; i++) {
                first[i] = arr[i];
            }
            T[] second = (T[]) new Comparable[arr.length - middle];
            for (int i = 0; i < second.length; i++) {
                second[i] = arr[middle + i];
            }
            return merge(mergesortHelper(first), mergesortHelper(second));
        }
    }

    /**
     * Merges two sorted arrays and maintains the ascending order.
     *
     * @param first  first array being merged.
     * @param second  second array being merged.
     * @return merged array.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<? super T>> T[] merge(T[] first, T[] second) {
        T[] merged = (T[]) new Comparable[first.length + second.length];
        int f = 0;
        int s = 0;
        while (f + s < merged.length) {
            if (f >= first.length) {
                merged[f + s] = second[s];
                s++;
            } else if (s >= second.length) {
                merged[f + s] = first[f];
                f++;
            } else if (second[s].compareTo(first[f]) < 0) {
                merged[f + s] = second[s];
                s++;
            } else {
                merged[f + s] = first[f];
                f++;
            }
        }
        return merged;
    }

    @Override
    public int[] radixsort(int[] arr) {
        if (arr.length == 0 || arr.length == 1) {
            return arr;
        }
        int min = findMin(arr);
        if (min < 0) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i] - min;
            }
        }
        radixsortHelp(arr);
        if (min < 0) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i] + min;
            }
        }
        return arr;
    }

    protected int[] radixsortHelp(int[] arr) {
        int i, m = arr[0], exp = 1, n = arr.length;
        int[] b = new int[10];
        for (i = 1; i < n; i++) {
            if (arr[i] > m) {
                m = arr[i];
            }
        }
        while (m / exp > 0) {
            int[] bucket = new int[10];

            for (i = 0; i < n; i++) {
                bucket[(arr[i] / exp) % 10]++;
            }
            for (i = 1; i < 10; i++) {
                bucket[i] += bucket[i - 1];
            }
            for (i = n - 1; i >= 0; i--) {
                b[--bucket[(arr[i] / exp) % 10]] = arr[i];
            }
            for (i = 0; i < n; i++) {
                arr[i] = b[i];
            }
            exp *= 10;
        }
        return arr;
    }

    /**
     * Finds the length of the maximum element in the array.
     *
     * @param arr  array being searched.
     * @return maximum length of the numbers in the array.
     */
    private int findMaxLength(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int length = 0;
        while (max > 0) {
            max /= 10;
            length++;
        }
        return length;
    }

    private int findMin(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
}