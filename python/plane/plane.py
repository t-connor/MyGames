# -*- coding: utf-8 -*-
import pygame
import random
from sys import exit

class Bullet:
    def __init__(self):
        self.x = 0;
        self.y = -1
        self.image = pygame.image.load('bullet.jpg').convert()
        self.active = False

    def move(self):
        if self.y < 0:
            self.active = False
        else:
            if self.active:
                self.y -= 3

    def restart(self):
        mouseX, mouseY = pygame.mouse.get_pos()
        self.x = mouseX - self.image.get_width()/2
        self.y = mouseY - self.image.get_height()/2
        self.active = True
#end of class Bullet

class Enemy:
    def __init__(self):
        self.restart()
        self.image = pygame.image.load('enemy.jpg').convert()
        
    def restart(self):
        self.x = random.randint(50, 400)
        self.y = random.randint(-200, -50)
        self.speed = random.random() + 0.1
        
    def move(self):
        if self.y < 800:
            self.y += self.speed
        else:
            self.restart()

        if self.x < 600:
            self.x += random.randint(-3, 3)
#end of class Enemy


class Plane:
    def __init__(self):
        self.x = 250
        self.y = 600
        self.image = pygame.image.load('plane.jpg').convert()

    def move(self):
        mouseX, mouseY = pygame.mouse.get_pos()
        #print "%d,%d" ,(mouseX, mouseY)
        if mouseY == 0 and mouseX == 0:
            self.x = 250
            self.y = 600
        else:
            self.x = mouseX - self.image.get_width()/2
            self.y = mouseY - self.image.get_height()/2
#end of class plane
        
def checkHit(enemy, bullet):
    if (bullet.x > enemy.x and bullet.x < enemy.x + enemy.image.get_width()) and (bullet.y > enemy.y and bullet.y < enemy.y + enemy.image.get_height()):
        enemy.restart()
        bullet.active = False
        return True
    return False

def checkBoom(enemy, plane):
    width = enemy.image.get_width()
    height = enemy.image.get_height()
    if (((plane.x + width > enemy.x and plane.x + width < enemy.x + width)and(plane.y + height > enemy.y and plane.y + height < enemy.y + height))\
        or((plane.x > enemy.x and plane.x < enemy.x + width)and(plane.y > enemy.y and plane.y < enemy.y + height))\
        or((plane.x + width > enemy.x and plane.x + width < enemy.x + width)and(plane.y > enemy.y and plane.y < enemy.y + height))\
        or((plane.x > enemy.x and plane.x < enemy.x + width)and(plane.y + height > enemy.y and plane.y + height < enemy.y + height))):
        ##print 1
        return True
    return False



#start the game
pygame.init()
screen = pygame.display.set_mode((600, 800), 0, 32)
pygame.display.set_caption("Hit Plane")
background = pygame.image.load('bg.jpg').convert()
IsGameOver = False
font = pygame.font.Font(None, 32)
score = 0

#init plane
plane = Plane()

#init bullet
bullets = []
for i in range(50):
    bullets.append(Bullet())
count_b = len(bullets)
index_b = 0
interval_b = 0

#init enemy
enemy = []
for i in range(10):  ##敌人变强
    enemy.append(Enemy())
##count_e = len(enemy)
##index_e = 0
##interval_e = 0


while True:
    for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
    if IsGameOver == False:                
        interval_b -= 1

        if interval_b < 0:
            bullets[index_b].restart()
            interval_b = 50    #开挂
            index_b = (index_b + 1)%count_b

        screen.blit(background, (0, 0))
        x, y = pygame.mouse.get_pos()

        for b in bullets:
            if b.active:
                for e in enemy:
                    if checkHit(e, b):
                        score += 1
                b.move()
                screen.blit(b.image, (b.x, b.y))

        for e in enemy:
            if checkBoom(plane, e):
                IsGameOver = True
            e.move()
            screen.blit(e.image, (e.x, e.y))

        plane.move()

        screen.blit(plane.image, (plane.x, plane.y))

        #display the score
        text = font.render("Socre: %d" % score, 1, (0, 0, 0))
        screen.blit(text, (0, 0))
    else:
        text = font.render("GAME OVER", 1, (255, 0, 0))
        screen.blit(text, (250, 400))
        text = font.render("your socre: %d" % score, 1, (255, 0, 0))
        screen.blit(text, (250, 450))
    pygame.display.update()

