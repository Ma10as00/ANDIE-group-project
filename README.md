# ANDIE, Return 'A+';

## Team Members

The team behind this ANDIE, Return 'A+';, consists of Michael Campbell, James Liu, Mathias Øgaard, Stella Srzich and Katie Wink. We met every week on Fridays to set goals for the week and reflect on our progress, and on Tuesdays to complete lab tasks and touch base. The team worked in a democratic style, where all team members had a say in the features of ANDIE and who should be responsible for each task. However, the team was somewhat led by Stella, who kept track of progress and had a big-picture view of the project. 

Each team member had the following responsibilities:
- Michael: Multilingual support and image resize
- James: Brightness/contrast adjustment and testing
- Mathias: Image rotations and flips
- Stella: Filters and error avoidance/prevention
- Katie: Image export and error avoidance/prevention

Note, however, all team members helped others with their responsibilities from time to time. And, we were all responsible for testing the project through interaction with the GUI, exception handling, and correct use of documentation within our classes.


## Testing

We tested ANDIE through inserting print statements, interaction with the GUI and keeping a testing log. In the beginning, when we started to implement our features, we each focused on making sure our features worked for expected inputs. So, we only interacted with the GUI, acting as a perfect user, meaning we had a baseline to improve on. We also entered statements within methods to print to the command line to make sure values, such as floats in a kernel, widths and heights of an image, or values returned by methods, were matching what we expected. These print statements helped us validate that the expected behaviour was (or was not) occuring, even when we could not check it with our bare eyes.

Then, once all features were implemented, James tested how they interacted together. He thoroughly applied many combinations of different actions after each push was made, and then texted on the group chat to let us know if anything unexpected occurred. Then, whoever was responsible for the feature could go in and fix it. The result of this testing can be seen under the 'tests' folder in 'testing_log.txt'. This txt file keeps track of what bugs were fixed, what bugs could be fixed, and the current behaviour of ANDIE. Concurrently, once features worked in the perfect case, all team members tested by interacting with the GUI, acting as normal, fallible users. Then, we noted down any issues, including ones that crashed the program, such as opening non-image files or entering non-ints for the radius of a filter, and ones that could cause problems for the user, such as allowing them to exit without reminding them to save changes or apply a resize with no image open.

Once we thought all bugs had been dealt with, and that the program ran perfectly, we got our friends, who aren't computer science majors, to test ANDIE. They provided feedback on the user experience and revealed bugs we had never noticed before. 


## User Guide

!["ANDIE Application"](/pictures/ANDIE_GUI.png)

### Description
‘ANDIE’ stands for A Non-Destructive Image Editor. The purpose of the program is to edit and manipulate images – like Photoshop but much, much simpler. The approach taken in ANDIE is non-destructive image editing. Many image processing operations, such as blurring filters, once applied to an image, cannot be reversed because information is lost in the process. Non-destructive image editors take the approach of storing the original image and the sequence of operations applied. Then, the operations can be applied to the original image to get the desired result. But since the original image and the full sequence of operations have been kept separately, no information is lost. ANDIE supports English, Māori and Norwegian, which you can select under the '**Language**' menu.

### Opening and Saving Images
ANDIE supports editing images with image file formats of PNG. Images are stored as image files, as you would expect. And, operations, which are the operations you may have applied to edit an image, are stored as operations files of the format png.ops.

You can use '**Open**', under the '**File**' menu to open an image file, possibly with operations applied as well. Note, this may not open an image how you would expect in a typical image editor.

- '**Open**' will open the selected image file, and will automatically open an associated operations file if it exists in the same directory. An operations file is associated with an image file if it has the same name as the image file with a '.ops' at the end. So, for example, if you open an image file called 'cat.png', and there is an operations file called 'cat.png.ops' in the same directory, both the image file and operations file will be opened. This means that if you edited the image 'cat.png' and clicked '**Save**' before, when you open the image 'cat.png' again, the operations you applied will automatically be reapplied and you will see the edited image. If there is no associated operations file, however, ANDIE will just open the image file, and you will see the unedited image.

Then, once you have applied operations to edit the image and are happy with the result, you can choose to save it by using '**Save**', '**Save As**' or '**Export**', under the '**File**' menu. Note that each of these is quite different, and may not behave how you would expect in a typical image editor.

- '**Save**' will save the operations file, which holds the operations currently applied to edit the image. This will be automatically called the name of the image file you opened with a '.ops' appended at the end. It will not do anything to the image file you opened. So, for example, if you open an image file called 'cat.png', apply some operations to edit the image, and then click '**Save**', ANDIE will save the operations in an operations file called 'cat.png.ops'. This means that the next time you open the image in ANDIE, you will see the edited image, but, the image file will remain unchanged.

- '**Save As**' will do the same thing as '**Save**', however, it will allow you to choose the name of the image and operations files. This means that a new image file identical to the original image file and the operations applied to the image will be saved under the given name. So, for example, if you open an image file called 'cat.png', apply some operations to edit the image, and then click '**Save As**', and enter the file name 'grey_cat.png', ANDIE will save the operations in an operations file called 'grey_cat.png.ops', and will save an image file identical to 'cat.png' called 'grey_cat.png'. Note, however, this means that any further operations will be acting on 'grey_cat.png'. And, if you open the image 'cat.png' in ANDIE, the image will not appear edited according to the operations saved as, as it is not associated with the operations file 'grey_cat.png.ops'.

- '**Export**' will allow you to export your edited image as a new image file, with the operations applied. So, for example, if you open an image file called 'cat.png', apply some operations to edit the image, and then click '**Export**', and enter the file name 'grey_cat.png', ANDIE will save the new edited image in an image file called 'grey_cat.png'. This means you will have a new image file of the edited image. Note, however, any further operations to edit the image will still be acting on 'cat.png', as that is the image file you have open.

### Editing Images
To edit an image, ANDIE allows you to apply operations to filter the image and to change the orientation, size and colour of the image. These operations appear under the menus '**Orientation**', '**Resize**', '**Filter**', and '**Colour**'.

- The '**Orientation**' menu allows you to rotate and flip your image. You can rotate your image by 90 degrees to the left or right, or by 180 degrees. You can also flip your image along its horizontal or vertical axes. 

- The '**Resize**' menu allows you to resize your image.  You can resize your image by 50%, making its width and height half of its original width and height. You can also resize your image by 150%, making its width and height one and a half times its original width and height. You can also choose to do a custom resize, where you can select the percent you would like to resize your image by. Note that these operations will change the width and height of the edited image. This is in contrast to the '**Zoom**' menu, which will not change the width and height of the edited image but will change how you view the edited image on your screen.

- The '**Filter**' menu allows you to apply various filters to your image. You can apply a sharpen filter, which sharpens the image. You can also apply blur filters, which will blur an image according to the radius you have provided. Note, a larger radius creates a stronger blur. Each of the blur filters creates a slightly different blur; The mean filter, which blurs the image by essentially averaging the pixel values, creates a typical blur. The median blur filter creates a blockier blur. And, the Gaussian blur filter creates a more natural blur.

- The '**Colour**' menu allows you to change the colour of the whole image. You can convert your image to greyscale, removing all colour from the image. You can also change the colour and brightness of your image according to the amount you have selected.

If you would like to undo or redo an operation you have applied to your image, you can do so under the '**Edit**' menu. Note, however, you can only undo and redo operations that you have applied since you opened an image file, or that have been saved in an operations file associated with the image file.


## Note

We slightly changed the structure of the Andie and FileActions classes. However, the code should still build and run akin to the skeleton code that was provided. The Andie class now has a private static data field of type ImagePanel called imagePanel, which is initialised in the method createAndShowGUI. This was only so that a private static method that was added to Andie, called frameClosing, could access the ImagePanel. Thus, it can warn the user that any unsaved changes will be lost if they close the window, only if there is an image open. We also added private data fields of type Jframe called frame to FileActions, ReszieActions, OrientationActions and EditActions which are initialised in their respective constructors. This was only done so that the main GUI frame could be packed around an image when it is opened, resized, rotated, or when an image operation is undone or redone that is a rotation or resize.
