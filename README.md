# Pre-work - SimpleTodo

SimpleTodo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Mark(weixiao) Huang

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] List anything else that you can get done to improve the app functionality!
   1, Add alertdialog for long click, only delete when user clicks "ok"
   2, when user adds item, it will go to dialogfragment first to edit
   3, Add support for sorting listview with priority or due date

Future work: add due date notification and notes for each item if needed

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/LQCBxlo.gif' title='LQCBxlo' width=''369' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I had some expperience developing android app preivously, but got to understand the framework and how things really work further this time. Android's approach and layouts and user interfaces are more intuitive, convenient and powerful, though constant updates required sometimes is time-consuming, but understandable.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** Adapter, like it is name, is a bridge between view and model so that data source and view objects are connected. For ArrayAdapter, it basically selects and converts arraylist of items to list of view ojbects so that it has control the content as well as how to display it. It is important to seperate view from other functions so that it is just a UI element without data in it.  ConvertView is to reuse any old views that leaves screeon if available so that it can reduce memory usage and improve displaying performance.

## Notes

Describe any challenges encountered while building the app.

It is quite challenging to get a good UI/UX layout and also there are still plenty can be done. Datepicker/calender and what format to store date in SQlite took me some time to make it work properly. Overall Stack Overflow and Google help me a lot. 

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.