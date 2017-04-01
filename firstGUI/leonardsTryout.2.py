from Tkinter import Tk, Canvas, Frame, BOTH


class Example(Frame):
  
    def __init__(self, parent):
        Frame.__init__(self, parent)   
         
        self.parent = parent        
        self.initUI()
        
        
    def initUI(self):
      
        self.parent.title("Shapes")        
        self.pack(fill=BOTH, expand=1)

        canvas = Canvas(self)
        canvas.create_oval(10, 10, 80, 80, outline="gray", fill="gray", width=2)
        
            
        points = [150, 100, 200, 120, 240, 180, 210, 200, 150, 150, 100, 200]
        canvas.create_polygon(points, outline='gray', fill='gray', width=2)
        
        canvas.pack(fill=BOTH, expand=1)


def main():
  
    root = Tk()
    ex = Example(root)
    root.geometry("330x220+300+300")
    root.mainloop()  


if __name__ == '__main__':
    main() 