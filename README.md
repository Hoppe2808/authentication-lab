# authentication-lab

## Password file management

If you haven't done it, create a user that will be used to run this application:
Create a group for the process first:
`groupadd processes`
`useradd -g processes printerserver`


Then set the owner of the password file to `printerserver`:
`chown printerserver:processes PASSWORD.txt`

Then set the permission so that only printerserver can read, modify and execute the file:
`chown 700 PASSWORD.txt`

Then run the server as `printerserver`
