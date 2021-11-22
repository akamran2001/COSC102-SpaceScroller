# Space Scroller Game I made for my COSC 102 Java Class in Spring'20. 
- To run the game, clone the repo, compile all .java files and run the GameLauncher class. Alternatively run the shell script if using Linux/MacOS

### The user iteracts with multiple objects in the game:
- Asteroids -> If the user hits an asteroid their health goes down.
- Fuel -> If the user hits a fuel object their health goes up.
- Portal -> If the user hits a portal they're teleported to a different random location on the map.
- Hammer,Wrench,Drill,Plyer,Srewdriver -> The user gains points when they collect these tools. If the collect the necessary amount indicated at the bottom of the screen, they win the game.

# Extra features: Portal
The portal is an object in the game that teleports the user to a new random location when the user hits it. There is a teleport method that performs this operation.
# Extra features: Toolbox
The toolbox stores the tools the user must collect to win the game. The toolbox class uses an integer array to represent the toolbox. The tools are represented by integers (0-5). The method for displaying the tools in the toolbox is implemented by the CreativeGame class.

# Image sources:
- [portal.png](https://vignette.wikia.nocookie.net/kirby-bulborb/images/1/12/Blue_Portal.png/revision/latest/scale-to-width-down/340?cb=20151004085207)

- [explosion.png](https://cdn.clipart.email/111b051891c731b461b10982ae3e091c_transparent-explosion-images-free-download-png-clipart-free-_2000-1940.png)

- [game_over.png](https://www.stick-it-easy.com/wp-content/uploads/2018/01/sticker-auto-moto-humour-gameover-01-262x262.png)

- [rocket.gif](https://cdn130.picsart.com/300019132101211.png?type=webp&to=min&r=1024)(other variations of the rocket image are edits of this image)

- [fuel.png](https://gamepedia.cursecdn.com/honkaiimpact3_gamepedia_en/f/fb/Armada_Fuel.png)

- [space.jpg](https://external-preview.redd.it/Kp6avW5v3hPBFnLqyrUy95LhFv7Jd3c-0-CuYEge48k.jpg?auto=webp&s=780d9804fa1d9e287ee326b941181a30956d768b)

- [coin.png](https://opengameart.org/sites/default/files/Coin%20256x256.png)

- [asteroid.gif](https://icons.iconarchive.com/icons/goodstuff-no-nonsense/free-space/256/asteroid-2-icon.png)


*Game can glitch on launch sometimes. Just quit and restart to solve this*
