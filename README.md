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
1.