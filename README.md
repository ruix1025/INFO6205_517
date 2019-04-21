# INFO6205 Final Project_517


**Team members:**

- Rui Xia     001417136
- Yumeng Xiao 001443049
- Yixuan Wang 001494410 



**Description:**

The topic of our group is using GAs algorithm to simulate image generation. 
Our target graph is a image whose shape is a panda and just with 2 colors: white and black. 
Our program will read the image and produce the corresponding Boolean array to store
the information of RGB/ color point in this image. And then we will initiate every 
row (individual) array in the first generation randomly, push them into the 
simulation of the Genetic Algorithm, randomly change the RGB/color point (gene) of 
individual by natural Mutation and Crossover based on different probabilities, 
select each set of rows (generation) in the process of evolution based on 
the fitness function. Eventually, we will tend to select the ideally best row
and make them form the best graph.