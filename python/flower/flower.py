#cidong : utf-8

import pygame
import random
from sys import exit


tableHeight = 600
tableWeight = 400
flowerRadius = 20
global score
score = 0

def dist(x1,x2,y1,y2):
    return ((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5


class Flower:
    def __init__(self):
        self.restart() 

    def flowerMove(self):
        xMouse = -10
        yMouse = -10
        pressed_array = pygame.mouse.get_pressed()
        if pressed_array[0]:
            xMouse, yMouse = pygame.mouse.get_pos()
        
        if self.active and (not self.Isclick):
            if self.x >= 0 and self.y >= 0 and self.x <= (tableWeight - flowerRadius) and self.y <= (tableHeight - flowerRadius):
                self.x += self.xSpeed 
                self.y += self.ySpeed
            if self.x < 0 or self.x > (tableWeight - flowerRadius):
                self.xSpeed = -self.xSpeed 
                self.ySpeed = self.ySpeed + random.uniform(-0.01, 0.01)
                self.x += self.xSpeed
                self.y += self.ySpeed
            if self.y < 0 or self.y > (tableHeight - flowerRadius):
                self.xSpeed = self.xSpeed + random.uniform(-0.01, 0.01)
                self.ySpeed = -self.ySpeed 
                self.x += self.xSpeed
                self.y += self.ySpeed

            self.myTimer = self.myTimer + 1
            if  self.myTimer == 100:
                self.image = pygame.transform.rotate(self.image, 90)
                self.myTimer = 0

            if xMouse >= self.x and yMouse >= self.y and xMouse < (self.x + flowerRadius) and yMouse < (self.y + flowerRadius):
                if not self.Isclick:
                    self.Isclick = True
                    self.flowerLargen()
        elif self.Isclick:
            self.flowerLargen()
                

    def flowerLargen(self):
        ##for i in range(100):
        global score
        score += 1
        self.image = pygame.transform.scale(self.image, (50, 50))

    def restart(self):
        self.x = random.randint(10, 390)
        self.y = random.randint(10, 390)
        self.xSpeed = random.uniform(-0.1, 0.1)
        self.ySpeed = random.uniform(-0.1, 0.1)
        self.active = True
        self.myTimer = 0
        self.image = pygame.image.load('flower.png').convert()
        self.Isclick = False


if __name__ == '__main__':
    global score
    pygame.init()
    screen = pygame.display.set_mode((tableWeight, tableHeight), 0, 32)
    pygame.display.set_caption('Flower')
    background = pygame.image.load('bg.jpg').convert()
    IsGameOver = False
    font = pygame.font.Font(None, 32)
    

    flowers = []
    for i in range(50):
        flowers.append(Flower())
    count_flower = len(flowers)

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
                
        if IsGameOver == False:
            screen.blit(background, (0, 0))

            for f1 in flowers:
                if f1.active:
                    f1.flowerMove()
                    screen.blit(f1.image, (f1.x, f1.y))
                    
            text = font.render("Socre: %d" % score, 1, (0, 0, 0))
            screen.blit(text, (0, 0))
             
            for f2 in flowers:
                for f3 in flowers:
                    if (abs(f3.x - f2.x) < 60 and abs(f3.y - f2.y) < 60):
                        if f2.Isclick and not f3.Isclick:
                            if dist(f3.x+10, f2.x+25, f3.y+10, f2.y+25) < 50:
                                f3.Isclick = True
                                f3.flowerMove()
                                screen.blit(f3.image, (f3.x, f3.y))
                                
             
        else:
            text = font.render("GAME OVER", 1, (255, 0, 0))
            screen.blit(text, (250, 400))
            text = font.render("your socre: %d" % score, 1, (255, 0, 0))
            screen.blit(text, (250, 450))

        pygame.display.update()
