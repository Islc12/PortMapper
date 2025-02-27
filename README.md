Author: Richard Smith
Date dev started: 2/26/2025
contact: (richrsmith@proton.me)

This will end up becoming the README file for the port mapper program, as I work on this project more and more I'll add more to this about the functionality of the program.

In the meantime it will serve as an outline to show what needs to be worked on in order to make this project function as well as highlighting the goal for the program.

----------------------------------------------------------------------------------------------------------
### PURPOSE
During the 2025 Virginia Cyber Range CTF event we were presented with an "IRL" (in real life) challenge that could earn your team points for two separate parts.
The first part involved using ssh to connect to a remote server. This was a fairly simple process as it just involved typical ssh usage. Though there was "tough" 
part and that involved decoding a hex value that returned a strange character when decoded to ASCII. However if decoded to decimal it would return a number that 
was within the valid port range. Then of course you do have to remember to change permissions for private keys in order for ssh to connect. After all was said 
and done the command came out to `ssh -i ~/Downloads/Germanna_CC.priv -p 20022 Germanna_CC@ctf.lan -v`, I chose to use the verbose option as only 1 person could
logon at a time and verbose allowed me to see that at least ssh was trying to connect.

The second part is where things got tricky. The server we ssh into was hooked up to a board with a bunch of tiny LED lights, it measured out to be a table of
128 lights across and 32 lights down, for a total of 4,096 lights. These lights were representative of network ports. The challenge was that certain lights were
illuminated blue while others were not at all. The goal was simple, scan the network ports that were lit up blue but don't scan the unlit ports. Originally I wrote 
an nmap script using Bash that would allow the user to scan a network accepting ports or a range of ports from input, as the information came back that a port was
open it would save that port number to a file, so the user could scan a large array of ports and later call that file with the saved open ports to only scan those.
However, when I got there I found out all 4,096 were actually open and so this method wasn't going to work. Thinking about it over the 2 hour ride back I came up with
an idea. Why not create an interactive GUI that would allow the user to click on the lights so that they could scan in whatever pattern they chose to? And so I began
thinking what the best course of action to take for this would be. Ultimately I decided on creating this program with java, I have python and bash experience, but I've
never played with the GUI components that they offer. Likewise it's been a while since I've developed anything with java and to be fair my java portfolio is lacking.
Also I feel as though the JavaFX library contains a very throughough amount of utilities that will allow me to customize this program in ways that python or bash couldn't.

----------------------------------------------------------------------------------------------------------
### OUTLINE
1. Install and configure JavaFX
2. Import JavaFX utilities
    a. Need a table/grid
    b. Need buttons
    c. Need to be able to change button colors when pressed
3. Import ArrayList for holding port numbers
4. Import java library to run external commands within the program
5. Set the stage (full screen, lets to 1920 x 1080)
6. Build the scene (simple black background)
7. Create the table
    a. 128 buttons per row
    b. 32  columns
8. Fill the table with buttons
9. Assign buttons to integer values of port numbers depending on position
    a. left to right, top to bottom
10. Build color change feature into buttons to differentiate on/off
11. Insert a "clear" button to reset the board
12. Insert a "start" button to begin scanning
13. Build an Nmap script to scan for ports within the ArrayList
14. Link the Nmap script to the main java program
15. **reserved**

----------------------------------------------------------------------------------------------------------