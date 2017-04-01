from Tkinter import Tk, Canvas, Frame, BOTH

from Tkinter import *

import threading

class ExplenationDisplay(Frame):
  
    def __init__(self, parent):
        Frame.__init__(self, parent)
        self.parent = parent        
        self.initUI()
        
        
    def initUI(self):
      
        self.parent.title("Shapes")        
        self.pack(fill=BOTH, expand=1)

        self.canvas = Canvas(self)
                
        self.canvas.pack(fill=BOTH, expand=1)

def mouseHandler(event):
    try:
        ed.canvas.delete("all")
    except:
        pass
    lastID = ed.canvas.create_oval(event.x, 30, event.x+30, 60, outline="red", fill="red", width=2)
    print ("clicked at", event.x, event.y)

lastID = 0

root = Tk()
ed = ExplenationDisplay(root)
root.bind('<B1-Motion>', mouseHandler)

sThread = threading.Thread(target=ed.mainloop, args=())
sThread.start()



