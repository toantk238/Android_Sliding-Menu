# Sliding-Menu

This sample describes how to make Sliding Menu as project https://github.com/jfeinstein10/SlidingMenu without customizing so much...
Because the above project now is not supported anymore, I hope every people feels happy with my one.

Email : freesky230892@gmail.com

How to make a Sliding Menu Activity
-----------------------------------

Just create a new Activity which is extended from BaseSlidingMenuActivity. In onCreate of your Activity, you have to use 
super.onCreate() because there is a default view set as content view. To display your content view, just pass it as the fragment and replace the Frame Layout 
with id content_layout ( you may see them in activity_sliding_menu.xml ).

For the left and right menu, you also pass them as fragment with id left_drawer and right_drawer.
Everything is really simple and easy, right ?


License
-------

Copyright 2016 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.