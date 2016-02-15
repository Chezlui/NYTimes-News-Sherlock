# Project 2 - *NYTimes News Sherlock*

**NYTimes News Sherlock** is an android app that allows a user to search for news on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **16.5** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
  * [x] Begin Date (using a date picker)
  * [x] News desk values (Arts, Fashion & Style, Sports)
  * [x] Sort order (oldest or newest)
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any image in results to see the full text of article **full-screen**
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [x] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [x] User can **share an article link** to their friends or email it to themselves
* [x] Replaced Filter Settings Activity with a lightweight modal overlay
* [ ] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [x] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [x] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [x] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] Leverage the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [x] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Use Parcelable instead of Serializable using the popular Parceler library. 

The following **additional** features are implemented:

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://s3-eu-west-1.amazonaws.com/chezlui.freelancer/codepath/NYTimesSherlock.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes
For recording all the filters I have opted for using SharedPreferences, that way is no need to be passing trough intents back and forward, and at the end of the day you need to saved them in some way, so this serves for both purposes.

For updating the FilterDialog with the date choosed by the user in DatePickerDialog I followed the next strategy. In first place, the filter dialog it registers itself as a Shared Preferences listener. In second place I created a new MyDatePickerDialog that extended from DatePickerDialog and overrided onDateChanged, so each time it was called I write the date in SharedPreferences. That way the Filter Dialog was called and then I could update the EditText where the date choosed was represented.

Staggered grid list was pretty straightforward. I thought of different strategies to implement the getItemViewType method. At the end the easier was just check if thumbnail was filled with an URL or not.

For the ItemDecorator I have to tune it a little since the code in Github was for a one column case.
## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Butterknife](https://github.com/JakeWharton/butterknife) - View "injection" library for Android
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling

## License

    Copyright [2016] [Jose Luis Martin Romera]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
