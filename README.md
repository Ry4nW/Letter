# 🖊️ Letter

<p style="text-align: center">A minimalist text editor for all your composition needs. Spend less time fiddling with technology and more time writing.</p> 

## Installation

Download and run the jar file in [out/production/](./out/production/Text Editor).

## Features

- Load functionality for .txt and source code files.
- Directly writes to your originally loaded file when you want to save.
- Creates a new `.txt` file to save your work in the case that you haven't loaded a file.
- A text area for writing, styling, sizing or font-switching your text.

## Technology

[JavaFX](https://openjfx.io/)
> an open source, next generation client application platform for desktop, mobile and embedded systems built on Java. It is a collaborative effort by many individuals and companies with the goal of producing a modern, efficient, and fully featured toolkit for developing rich client applications.

[InlineCSSTextArea from RichTextFX](https://github.com/FXMisc/RichTextFX)
> RichTextFX provides a memory-efficient text area for JavaFX that allows the developer to style ranges of text, display custom objects in-line (no more HTMLEditor), and override the default behavior only where necessary without overriding any other part of the behavior.

## Methods and Testing

All testing is done in [./testCases](./testCases).

**Methods tested:**
- `onLoad()` - checks for the correct loading of files into `InlineCSSTextArea` and file paths.
- `onSave()` - checks for the correct writing of files into a loaded file or saving of a new `.txt` file.
- `onClose()` - checks for the status of the client's work save and continues accordingly.
- `[styling]Text()`, `set[font]Font()`, `setFontSize[size]()` - checks the correct styling and consistency of text styling,
fonts and font sizes.


