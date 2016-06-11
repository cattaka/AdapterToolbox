AdapterToolbox
==================
This is utility classes for RecyclerView and classic AdapterView(such as ListView, Spinner).

- ScrambleAdapter
 - This separate creating ViewHolder logic from RecyclerView.Adapter.
 - Once gave DataClass-ViewHolder relation, this will bind them automatically.
 - The relation of DataClass-ViewHolder are declared by ViewHolderFactory.
- ViewHolderFactory
 - Describe the relation of DataClass-ViewHolder.
 - This has onCreateViewHolder() / onBindViewHolder() / isAssignable() / etc methods.
- ListenerRelay
 - Empty implementation of frequently used listeners. This is used by ScrambleAdapter.
- CodeLabelAdapter
 - The adapter for Enum. But you can adopt this to other standard data classes.
- ClassicScrambleAdapter
 - Classic version of ScrambleAdapter. It can go with classic AdapterView(such as ListView, Spinner).
- AbsTreeItemAdapter
 - This provides foldable tree list.
- AbsChoosableTreeItemAdapter
 - This supports single choice and multi choice mode.


## How to use
Add folloing lines to build.gradle

```groovy
repositories {
    maven {
        url "http://dl.bintray.com/cattaka/maven"
    }
}

dependencies {
    compile 'net.cattaka:adapter-toolbox:$VERSION@aar'
}
```

Put $VERSION that you want to use.
You can check available versions here. https://bintray.com/cattaka/maven/adapter-toolbox/view


## Examples

This repository contains example application.
You can clone this repository and run example application with Android Studio.

### Header and footer
![Header and footer](https://cloud.githubusercontent.com/assets/1239253/15986438/ec53b2ee-3042-11e6-975b-4e123135c293.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/RecyclerViewHeaderExampleActivity.java)

### Sections within a RecyclerView
![Sections within a RecyclerView](https://cloud.githubusercontent.com/assets/1239253/15986463/75959b6c-3043-11e6-8959-d1477f6e6156.png)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/MultiAdapterExampleActivity.java)

### Multiple button in single row
![Multiple button in single row](https://cloud.githubusercontent.com/assets/1239253/15986467/a2b9a412-3043-11e6-8b39-277aeb78858c.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/ComplexStringExampleActivity.java)

### Scramble several data types
![Scramble several data types](https://cloud.githubusercontent.com/assets/1239253/15986474/cf78938c-3043-11e6-81df-05edbc29b499.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/ScrambleAdapterExampleActivity.java)

### Manipulable items within RecyclerView
![Manipulable items within RecyclerView](https://cloud.githubusercontent.com/assets/1239253/15986483/ed4f72d6-3043-11e6-81bb-4831991f727d.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/ManipulableListExampleActivity.java)
- [Code(DataBinding version)](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/DataBindingManipulableListExampleActivity.java)

### Expandable tree items within RecyclerView
![Expandable tree items within RecyclerView](https://cloud.githubusercontent.com/assets/1239253/15986490/31715650-3044-11e6-96dd-0d49a4edb394.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/MultiChoosableTreeItemAdapterExampleActivity.java)

### Dynamically change ItemViewType
![Dynamically change ItemViewType](https://cloud.githubusercontent.com/assets/1239253/15986501/60edcb3e-3044-11e6-88ee-ab79660cac02.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/FizzBuzzExampleActivity.java)

### Expandable tree within Spinner
![Expandable tree within Spinner](https://cloud.githubusercontent.com/assets/1239253/15986505/7a902fbe-3044-11e6-956c-d2efa3fe2fb7.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/SpinnerTreeItemAdapterExampleActivity.java)


### Headered list within Spinner
![Headered list within Spinner](https://cloud.githubusercontent.com/assets/1239253/15986510/91ae9e4c-3044-11e6-81e6-a42489350179.gif)

- [Code](https://github.com/cattaka/AdapterToolbox/blob/master/example/src/main/java/net/cattaka/android/adaptertoolbox/example/SpinnerScrambleAdapterExampleActivity.java)


### JUnit test with Espresso
[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/FSx8rZnBB0E/0.jpg)](http://www.youtube.com/watch?v=FSx8rZnBB0E)

- [Code](https://github.com/cattaka/AdapterToolbox/tree/master/example/src/androidTest/java/net/cattaka/android/adaptertoolbox/example)


## License

```
Copyright 2016 Takao Sumitomo
Copyright 2014 Josh Burton

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
