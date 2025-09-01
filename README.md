<h1>Playlist Manager</h1>

<h2>Overview</h2>
This playlist manager is a desktop application created using 
JavaFX and SQLite that allows users to create, manage, 
and view playlists and songs. This was developed as my final
project submission to Professor Chris Cantera's Introductory 
Software Projects class to demonstrate basic understanding of
software development and the software development cycle.

<h2>Features</h2>
<ul>
<li>Create and manage playlists</li>
<li>Add and Remove songs</li>
<li>Search for songs/playlists by name</li>
<li>View all added songs or all added playlists</li>
<li>Export all added songs/playlists to HTML or PDF files</li>
<li>Help window that explains features in depth</li></ul>

<h2>Resources Used</h2>
<ul>
<li>Java (recommend JDK 17 or later)</li>
<li>JavaFX for UI building (Styled with CSS)</li>
<li>SQLite for database storage</li>
<li>Maven for dependency management (SQLite JDBC driver, SLF4J, 
PDF export tools)</li>
</ul>

<h2>Installation and Setup</h2>
<ol>
<li>Clone the repository by running the following command:
<code>git clone https://github.com/bygbuggygmoneybags/PRO100-Final.git
cd playlist manager</code></li>
<li>Open in your chosen IDE (Visual Studio Code, IntelliJ IDEA, 
etc.)</li>
<li>Install dependencies via Maven (They should resolve 
automatically)</li>
<li>Run the Application using PlaylistGUI.java</li>
</ol>

<h2>Database Structure</h2>
The application utilizes a SQLite database called PlaylistsDB
with the following tables: 
<ul>
<li>Songs</li>
<li>Playlists</li>
<li>PlaylistSongs (connecting table between playlists and their
songs to properly create many-to-many relationship)</li>
</ul>
If the database file does not exist, it will be automatically
generated during your first run of the program.

<h2>Usage</h2>
<ol>
<li>Create a playlist by clicking the Create button
in the toolbar</li>
<li>Click on the created playlist in the list of
playlists on the left to select it</li>
<li>Upon selecting a playlist, click the Edit button in the
toolbar</li>
<li>Click the Add Song button in the dropdown menu and enter 
the details of your song</li>
<li>In order to view a playlist, click the View button in the
toolbar, then select one of the following 
four options in the dropdown menu:
<ul>
<li>Search Song (this will be by name)</li>
<li>View All Songs (this will show all added songs</li>
<li>Search Playlist (this will be by name and will also
display all songs in the searched playlist)</li>
<li>View All Playlists</li>
</ul></li>
<li>In the View window that appears upon selecting a view 
option, the top toolbar will have the option to export the data
to either an HTML file or a PDF file</li>
<li>Click Help for more details on features</li>
</ol>

<h2>Author</h2>
This project was developed entirely by G Rodriguez-Rubio for
as a final project submission for the Introductory Software 
Projects course.