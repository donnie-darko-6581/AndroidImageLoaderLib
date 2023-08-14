# AndroidImageLoaderLib
Problem: Create android lib to fetch dog images and show them in an android app.

**Original problem statement:**
Create an Android library to fetch Dog images on the initialization of the library. The library 
should have a few helper methods listed below :-

getImage() - Gets one image of a dog from the library.
getImages(int number) - Gets the number of dog images mentioned in the method
getNextImage() - Gets the next image of a dog
getPreviousImage() - Gets the previous image of a dog


Create an Android app using Java/Kotlin and use the above library to demonstrate the helper methods. 
The app should have two buttons, “Next” and “Previous”. The “Previous” button should be inactive on 
the first image. Clicking on the “Next” button should randomly get an image of a dog from the 
library. Clicking on “Previous” should get the previous image. Add an input box to input a number 
between 1 & 10. Add a “Submit” button. The button should fetch the number of dog images mentioned 
in the input box.

Please handle all sorts of cases that you can think of when writing your solution.
Once your app is running using the library methods given above, bonus points will be given for 
adding unit test methods for the above-mentioned methods using the Test Framework of your choice. 

API Information:
API endpoint - https://dog.ceo/api/breeds/image/random
Sample JSON response -
{
    "message": "https://images.dog.ceo/breeds/leonberg/n02111129_2785.jpg",
    "status": "success"
}

Please refer to this Dog.ceo link for API documentation
