# CSI460_Midterm_MM
 
For my midterm, I decided to do a rudimentary version of an application where you can find adoptable dogs. I thought this would be a good way to use a SQLite database and implement some CRUD functionality with pages that interact through a simple UX flow and a classic top-to-bottom UI approach. There is a basic homepage, a page to create and add new dog information, another for displaying and selecting this information (List/recycler view), and one for updating and/or removing existing data.

The application opens to a very simple homepage with a made-up name (Matthew's Pet Rescue), an image, and a couple of buttons. One button goes to an activity where we can create a new dog, and the other goes to an activity to view all the adoptable dogs currently in our database.
![Capture1](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/efcf6a14-1cfd-4700-89aa-13458a3ff198)

When we click on the "Add New" button, we come to a layout page with some EditTexts to gather the dog's information from the user along with a button to add an image. This page also has two buttons, one for submitting the information to save to the database, and another to see the full list of dogs.
![Capture2](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/9ad4470d-8f52-42b0-880e-1860221d39ee)

There are a few validation checks. If I were to just try and submit without having everything filled out, it would give me a toast message that "one or more fields are missing". There is also a validation check that the input of the age is a digit and that the vaccination status can only be answered "Yes or No", but it's not case sensitive. The only one we don't need to enter is the image, it will just use the dog placeholder image if one is never chosen. 
![Capture4](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/935baf2f-e538-40f1-bbe9-33db8f59cd4e)

When clicking the choose image button, we see that we're taken to a page to select a photo. For the pictures, I just used a few free images from the internet and put them into the emulator storage using the device explorer (~ / storage / emulated / 0 / custom folder). Then they will show up in our virtual machine and can be used similarly to how we might choose a photo from our own phone.
![Capture3](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/4d1918d8-c2bc-4803-8c6e-ce0536c7e200)

After choosing an image and adding in all the relevant information, this is what it looks like.
![Capture5](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/c135f7b7-ff98-4309-b249-43cca4e873aa)

Once I hit submit and it gets saved, I can go to the list where we can see all the dogs currently in the database. The list is displayed using a recycler view which helps preserve memory and makes it easy to scroll vertically through the data, and down at the bottom, we can find the dog I just entered.
![Capture6](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/1cdaddaa-5e4e-4fa8-97ac-6a10003422a4)

If we want to make any changes to a dog, we can simply select or click on a dog from the list. In the picture below, we see an activity similar to the "Add Dog" layout, except this is the "Update Dog" page which has a different title at the top along with buttons for deleting and updating data. To show how it works, I made a couple of changes using the same dog we created, but I changed its age from 5 to 6, and changed its vaccination status to "Yes".
![Capture7](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/59fe8a7d-a902-4a3d-9c5b-6d55ff7f34f1)

After clicking "Update" we can see how the changes are reflected in the list.
![Capture8](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/63ef13a3-46de-4480-b615-4bac7882f9d5)

In the case of removing a dog from the list, we just have to choose "Delete," but I also felt it appropriate to add an Alert Dialog with a question to confirm the deletion. 
![Capture9](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/3a994770-e260-4e64-ab37-3567d232f62b)

Using Db Browser, we can get a better overview of the database (dogsDb.db). In this case, we have our dogs table, which currently only has 4 dogs and 7 object properties for each, not including the primary key (Id). We can also see that the dog I had created earlier is not there, because I deleted it. One thing I ended up having to do was save the images as a byte array, which is why it is shown as a type BLOB (binary large object). However, I know this isn't the most efficient way to do things. I did try to save it as a URI path initially, but I kept running into issues when going to retrieve it from the database and kept getting errors having to do with certain permissions. I also tried using other libraries like Glide but with no luck. Some of these concepts are still new to me and I am still learning, but I decided to revisit this another time and just used a byte array instead. The only hassle with this was having to convert it back and forth between byte array and bitmap, which I learned I needed to do in order to display them in UI Components and be able to pass it using intent. This is something I will definitely revisit and continue to learn more about.
![Capture10](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/e75453ea-a266-49f5-897a-d8e3dde0e848)

Shown below is the overall structure of my code. I used 4 activity/layout pages for the Main, AddDog, UpdateDog, and DogsList activities, as well as one XML layout for the dog item. I also created a DbHandler class to help interact with the Sqlite database, an RclAdapter class to help display the list data, and a dog object class.
![Capture11](https://github.com/MMahar5/CSI460_Midterm_MM/assets/97249776/27dcee9d-211c-4a81-b1cf-8f2e90c8d63d)
