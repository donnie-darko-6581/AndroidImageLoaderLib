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


**Approach**

1. Used Android library for dog images fetching
2. it contains deps for lib like okhttp and retrofit and coroutines
3. did not package into aar and directly included in project - aar would need extra build code to include trans deps.
4. Implement api using retrofit - did not check ttl/security, happy cases only, need to handle errors
5. Impl DB as single source of truth
6. Repository class created to handle api and data both
7. DogImageLib class has all methods asked in problem statement
8. Prefetch logic added, first we get only 1 for quick response, followed by bulk images
9. Policy is created and can be passed by user based on Network/Data factors
10. Page number is kept internal to lib so app does not have to maintain state
11. Locking so that there is no race condition 
11. Activity implemented and used jetpack compose for UI
12. Prev and next buttons added
13. Center button fetches multiple images with input number. todo: limit the count
14. Added logcat output in repo 'fetchMulti.png' which shows multi fetch will return from repo not api
15. Started adding test cases - huge scope
16. Added test for view model



Notable TODOs:
The scope of problem statement is really big and there can be noticeable advances made in various 
things, mentioning things below which come on top of my mind.

1. Api failures not handled
2. No internet not handled
3. Download progress in fetch multiple missing
4. Testing is pending for classes
5. Policy support missing based on network
6. Other todos are mentioned
7. Fairly new to Jetpack compose, so learning it as I am working on this project
8. Deprecated testing methods used for coroutines due to lack of time
9. Missing app icons/name
10. Missing styling best practises, there might be some hardcoding
11. KSP could be used instead of Kapt, it requires more time to explore
12. Could avoid heavy libs in ImageLoaderLib to keep size small
13. Release best practices not followed


For more detailed insights follow commit history.