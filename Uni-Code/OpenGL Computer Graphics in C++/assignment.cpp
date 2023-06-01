#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <stb_image.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include <learnopengl/filesystem.h>
#include <learnopengl/shader_m.h>
#include <learnopengl/camera2.h>
#include <iostream>
#include <string>

#define PI 3.14159265

// Box coordinate with 36 vertices.
// Every 3 coordinates will form 1 triangle.
// The last 2 columns represent texture coordinate for mapping.

float box[] = {
	// positions          // normals           // texture coords
	-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,
	 0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  0.0f,
	 0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
	 0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
	-0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  1.0f,
	-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,

	-0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,
	 0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  0.0f,
	 0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
	 0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
	-0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  1.0f,
	-0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,

	-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
	-0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
	-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
	-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
	-0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
	-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

	 0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
	 0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
	 0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
	 0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
	 0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
	 0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

	-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,
	 0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  1.0f,
	 0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
	 0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
	-0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  0.0f,
	-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,

	-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f,
	 0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  1.0f,
	 0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
	 0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
	-0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  0.0f,
	-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f
};

void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void process_input(GLFWwindow *window);
void mouseCallback(GLFWwindow *window, double xpos, double ypos);
unsigned int loadTexture(char const * path);

bool firstMouse = true;
bool sprint = false;
double mousex;
// settings
const unsigned int SCR_WIDTH = 800;
const unsigned int SCR_HEIGHT = 800;

glm::mat4 projection;
glm::mat4 orthographic;
glm::mat4 perspective;
// camera
Camera camera(glm::vec3(0.0f, 1.2f, 3.0f));
float lastX = SCR_WIDTH / 2.0f;
float lastY = SCR_HEIGHT / 2.0f;
// lighting
glm::vec3 light_pos(0.0f, 1.0f, 0.1f);

// timing
float delta_time = 0.0f;	// time between current frame and last frame
float last_frame = 0.0f;

//Toggle (Animation or states)
int ACTION_DELAY = 0;
int attenuationControl = 3;
bool STAIRS_ACTIVATED = false;
bool NEAR_STAIRS = false;
bool DROPPED_LIGHT = false;
bool SHOW_COORDINATE = false;
bool PERSPECTIVE_ON = true;
bool UP_BRIGHTNESS = false;
bool DOWN_BRIGHTNESS = false;
bool DARK_SCENE_ON = false;
bool bossDefeated = false;
float brightness = 1.5f;


//Animation Variables
float stair_translate = 0.0f;
float crystal_rotate = 0.0f;
float bossRotate = 1.0f;
float defeatTranslate = 0.0f;
float defeatRotate = 0.0f;

// Countdown until the button trigger can be pressed again.
// This prevents accidental burst repeat clicking of the key.
void update_delay()
{
	if(ACTION_DELAY > 0) ACTION_DELAY -= 1;
}

// Toggle button pressing only if the camera is close enough.
void check_near_stairs()
{
	if(glm::length(camera.Position - glm::vec3(0.0f, 0.0f, -10.0f)) <= 2.0f)
		NEAR_STAIRS = true;
	else
		NEAR_STAIRS = false;
}

void updateBrightness(int identifier)
{
	if (identifier == 1 && brightness < 3.0f)
	{
		brightness += 0.1f;

	}
	else if (identifier == -1 && brightness > 0.0f)
	{
		brightness -= 0.1f;
		
	}
	UP_BRIGHTNESS = false;
	DOWN_BRIGHTNESS = false;
	if(brightness > 2.35f)
		attenuationControl = 5;
	else if (brightness > 2.0f)
		attenuationControl = 4;
	else if (brightness > 1.5f)
		attenuationControl = 3;
	else if (brightness > 1.0f)
		attenuationControl = 2;
	else if (brightness > 0.5f)
		attenuationControl = 1;
	else if (brightness == 0.0f)
		attenuationControl = 0;	
}


int main()
{
	// glfw: initialize and configure
	// ------------------------------
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

#ifdef __APPLE__
//	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // uncomment this statement for OS X
#endif

	// glfw window creation
	// --------------------
	GLFWwindow* window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "OpenGL Tutorial", NULL, NULL);
	if (window == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return -1;
	}
	glfwMakeContextCurrent(window);
	glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
	glfwSetCursorPosCallback(window, mouseCallback);
	// glad: load all OpenGL function pointers
	// ---------------------------------------
	if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
	{
		std::cout << "Failed to initialize GLAD" << std::endl;
		return -1;
	}

	// configure global opengl state
	// -----------------------------
	glEnable(GL_DEPTH_TEST);

	// build and compile our shader zprogram
	// ------------------------------------
	Shader lighting_shader("lighting.vs", "lighting.fs");
	Shader lamp_shader("lamp.vs", "lamp.fs");

	// set up vertex data (and buffer(s)) and configure vertex attributes
	// ------------------------------------------------------------------

	unsigned int VBO_box, VAO_box;

	glGenVertexArrays(1, &VAO_box);
	glGenBuffers(1, &VBO_box);

	glBindVertexArray(VAO_box);

	glBindBuffer(GL_ARRAY_BUFFER, VBO_box);
	glBufferData(GL_ARRAY_BUFFER, sizeof(box), box, GL_STATIC_DRAW);

	//vertex coordinates
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);
	//normal vectors
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(1);
	//texture coordinates
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(6 * sizeof(float)));
	glEnableVertexAttribArray(2);



	// second, configure the light's VAO (VBO stays the same; the vertices are the same for the light object which is also a 3D cube)
	unsigned int VAO_light;
	glGenVertexArrays(1, &VAO_light);
	glBindVertexArray(VAO_light);

	glBindBuffer(GL_ARRAY_BUFFER, VBO_box);
	// note that we update the lamp's position attribute's stride to reflect the updated buffer data
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);





	// load and create a texture 
	// -------------------------
	unsigned int tex_carpet_diffuse, tex_throne_diffuse, tex_swordwall_diffuse,tex_bookwall_diffuse,tex_metal_diffuse, tex_marble_diffuse;
	unsigned int tex_carpet_specular, tex_boss_specular, tex_throne_specular, tex_dark_texture, tex_metal_specular, tex_book_specular,tex_marble_specular;
	unsigned int tex_floor_diffuse, tex_wall_diffuse, tex_eyes_diffuse,tex_bossbody_diffuse,tex_bossabdomen_diffuse,tex_crytal_diffuse, tex_goldcoins;
	unsigned int tex_floor_specular, tex_wall_specular,tex_bookcover,tex_bookpages, tex_bookside, tex_fabric_specular, tex_tabletop, tex_wood, tex_tableside;
	unsigned int tex_chair_specular;

	
	tex_carpet_diffuse = loadTexture(FileSystem::getPath("resources/textures/carpet.png").c_str());
	tex_carpet_specular = loadTexture(FileSystem::getPath("resources/textures/carpet_specular.png").c_str());
	tex_floor_diffuse = loadTexture(FileSystem::getPath("resources/textures/floor.jpg").c_str());
	tex_floor_specular = loadTexture(FileSystem::getPath("resources/textures/floor_specular.jpg").c_str());
	tex_wall_diffuse = loadTexture(FileSystem::getPath("resources/textures/brickwall.jpg").c_str());
	tex_wall_specular = loadTexture(FileSystem::getPath("resources/textures/wall_specular.jpg").c_str());
	tex_throne_diffuse = loadTexture(FileSystem::getPath("resources/textures/Throne.jpg").c_str());
	tex_throne_specular = loadTexture(FileSystem::getPath("resources/textures/throne_specular.jpg").c_str());
	tex_fabric_specular = loadTexture(FileSystem::getPath("resources/textures/fabricspecular.jpg").c_str());
	tex_swordwall_diffuse = loadTexture(FileSystem::getPath("resources/textures/swordwall.png").c_str());
	tex_bookwall_diffuse = loadTexture(FileSystem::getPath("resources/textures/books.png").c_str());
	tex_dark_texture = loadTexture(FileSystem::getPath("resources/textures/ceiling_specular.jpg").c_str());
	tex_marble_specular = loadTexture(FileSystem::getPath("resources/textures/marble_specular.jpg").c_str());
	tex_marble_diffuse = loadTexture(FileSystem::getPath("resources/textures/marble.jpg").c_str());
	tex_bookcover = loadTexture(FileSystem::getPath("resources/textures/bookcover.png").c_str());
	tex_bookpages = loadTexture(FileSystem::getPath("resources/textures/bookpages.jpg").c_str());
	tex_bookside = loadTexture(FileSystem::getPath("resources/textures/bookside.png").c_str());
	tex_book_specular = loadTexture(FileSystem::getPath("resources/textures/bookspecular.jpg").c_str());
	tex_boss_specular = loadTexture(FileSystem::getPath("resources/textures/boss_spec.jpeg").c_str());
	tex_eyes_diffuse = loadTexture(FileSystem::getPath("resources/textures/spider_face.png").c_str());
	tex_bossbody_diffuse = loadTexture(FileSystem::getPath("resources/textures/body.jpg").c_str());
	tex_bossabdomen_diffuse = loadTexture(FileSystem::getPath("resources/textures/abdomen.jpg").c_str());
	tex_metal_diffuse = loadTexture(FileSystem::getPath("resources/textures/metal.png").c_str());
	tex_crytal_diffuse = loadTexture(FileSystem::getPath("resources/textures/teleportcrystal.jpg").c_str());
	tex_metal_specular = loadTexture(FileSystem::getPath("resources/textures/metal_specular.jpg").c_str());
	tex_tabletop = loadTexture(FileSystem::getPath("resources/textures/table.png").c_str());
	tex_wood = loadTexture(FileSystem::getPath("resources/textures/darkwood.jpg").c_str());
	tex_tableside = loadTexture(FileSystem::getPath("resources/textures/tableside.png").c_str());
	tex_goldcoins = loadTexture(FileSystem::getPath("resources/textures/goldcoins.jpeg").c_str());
	tex_chair_specular = loadTexture(FileSystem::getPath("resources/textures/chair cushion.png").c_str());
	//shader configuration -------------------------------------------------------------------------------------------
	lighting_shader.use();
	lighting_shader.setInt("material.diffuse", 0);
	lighting_shader.setInt("material.specular", 1);

	// Set initial projection
	// -----------------------------------------------------------------------------------------------------------
	//Projection settings 
	perspective = glm::perspective(glm::radians(45.0f), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 300.0f);
	orthographic = glm::ortho(-0.750, 0.750, 0.0, 1.2, 1.0, 100.0);

	int count = 0;
	// render loop
	// -----------
	while (!glfwWindowShouldClose(window))
	{
		if(PERSPECTIVE_ON == true)
			projection = perspective;
		else	
			projection = orthographic;
		lighting_shader.setMat4("projection", projection);
		// per-frame time logic
		// --------------------
		float currentFrame = glfwGetTime();
		delta_time = currentFrame - last_frame;
		last_frame = currentFrame;
		
		//update delay countdown
		update_delay();
	
		// input
		// -----
		process_input(window);

		// render
		// ------
		glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 


		// activate shader
		lighting_shader.use();

		if(DROPPED_LIGHT == true)
		{
			lighting_shader.setVec3("light.position", light_pos);
        		lighting_shader.setVec3("viewPos", light_pos);
		}
		else
		{
			lighting_shader.setVec3("light.position", camera.Position);
        		lighting_shader.setVec3("viewPos", camera.Position);
		}
		// light properties
		if (DARK_SCENE_ON){
			lighting_shader.setVec3("light.ambient", 0.1f, 0.1f, 0.1f);
			lighting_shader.setBool("scene", DARK_SCENE_ON);
		}else
		{
			//lighting_shader.setVec3("light.ambient", 1.8f, 1.8f, 1.8f);
			lighting_shader.setBool("scene", DARK_SCENE_ON);
		}	
			

		if (UP_BRIGHTNESS)
		{	
			updateBrightness(1);
		}
		else if (DOWN_BRIGHTNESS)
		{
			updateBrightness(-1);
		}

		if(DARK_SCENE_ON)
		{
			lighting_shader.setVec3("light.diffuse",  brightness, brightness, brightness);
			lighting_shader.setVec3("light.specular", 1.0f, 1.0f, 1.0f);
		}
		else
		{
			lighting_shader.setVec3("light.diffuse", 0.0f, 0.0f, 0.0f);
			lighting_shader.setVec3("light.specular", 0.0f, 0.0f, 0.0f);
		}

		float linear[] = {0.7f,0.35,0.22,0.14,0.09,0.07};
		float quadratic[] = {1.8f,0.44f,0.2f,0.032f,0.017};
		lighting_shader.setFloat("light.constant", 1.0f);
        lighting_shader.setFloat("light.linear", linear[attenuationControl]);
        lighting_shader.setFloat("light.quadratic", quadratic[attenuationControl]);
			
		// camera/view transformation
		glm::mat4 view = camera.GetViewMatrix();
		lighting_shader.setMat4("view", view);

		//declare transformation matrix
		glm::mat4 model = glm::mat4();

		//Check near stairs
		check_near_stairs();
		//Draw objects
		//------------------------------------------------------------------------------------------

		//Boss
		glm::vec3 boss_scales[] = {
			glm::vec3( 0.5f,  0.5f,  0.5f),  //Head
			glm::vec3( 1.05f,  1.5f,  1.0f),	//Body
			glm::vec3( 1.0f,  2.5f,  1.0f),   //Abdomen
			glm::vec3( 0.1f,  1.5f, 0.1f),  //Leg short	
			glm::vec3( 0.22f, 2.35f, 0.20f), //Leg long
			glm::vec3( 0.1f, 0.1f, 1.5f) //Spear
		};
		glm::vec3 boss_positions[] = {
			glm::vec3( 0.0f, 1.75f,  -0.15f),   //Head
			glm::vec3( 0.0f, 1.0f,  0.0f),   //Body
			glm::vec3( -0.0f, 1.6f, -0.75f),   //Abdomen
			glm::vec3( 0.75f,  1.0f,-0.40f),  //Leg short
			glm::vec3( 0.75f,  1.0f,0.0f) ,
			glm::vec3( 0.75f,  1.0f,0.40f),  
			glm::vec3( -0.75f,  1.0f,-0.40f),
			glm::vec3( -0.75f,  1.0f,0.0f) ,
			glm::vec3( -0.75f,  1.0f,0.40f) , 
			glm::vec3( -1.0f,  0.75f,0.40f),   //Leg Long
			glm::vec3( -1.0f,  0.75f,0.0f),  
			glm::vec3( -1.0f,  0.75f,-0.40f),
			glm::vec3( 1.0f,  0.75f,0.40f),
			glm::vec3( 1.0f,  0.75f,0.0f),  
			glm::vec3( 1.0f,  0.75f,-0.40f),        
			glm::vec3( 0.0f,  1.85f,0.75f) //Spear 
		};
		float legVariations[] = {
			-2.2f,
			1.2f,
			2.1f,
			2.3f,
			0.0f,
			1.1f,
		};
		unsigned int boss_textures[] = {
			tex_eyes_diffuse,
			tex_bossbody_diffuse,
			tex_bossabdomen_diffuse,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_dark_texture,
			tex_metal_diffuse
		};

		
		if(bossDefeated){
			if (count < 3){
				bossRotate -= 0.1f;
			}else{
				bossRotate -= 0.25f;
			}
			if(bossRotate < -10.0f){
				bossRotate = 1.0f;
				count += 1;
				if (count > 3)
				{
					count = 0;
				}
			}
		}else if(!bossDefeated){
			if(defeatRotate <= -58.4f && defeatTranslate >= 6.0f)
			{
				bossDefeated = true;
			}
			if(defeatRotate > -58.4f)
			{
				defeatRotate -= 0.4f;
			}
			if(defeatTranslate < 6.0f)
			{
				defeatTranslate += 0.0415f;
			}
		}


		glBindVertexArray(VAO_box);
		for(int boss = 0; boss < 16; boss++)
		{	
			model = glm::mat4();
			model = glm::rotate(model, glm::radians(30.0f), glm::vec3(0.0f, 1.0f, 0.0f));
			model = glm::rotate(model, glm::radians(defeatRotate), glm::vec3(1.0f, 0.0f, 0.0f));
			model = glm::translate(model, glm::vec3( 0.0f,  defeatTranslate,0.0f));
			model = glm::translate(model, boss_positions[boss]);
			
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, boss_textures[boss]);
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, tex_boss_specular);	
			
			model = glm::translate(model, glm::vec3(0.0f, 0.0f, -3.0f));
			if(boss == 2)
			{
				model = glm::rotate(model, glm::radians(-40.0f), glm::vec3(1.0f, 0.0f, 0.0f));
			}
			if(boss > 2 && boss < 6){
				model = glm::rotate(model, glm::radians(-25.0f), glm::vec3(0.0f, 0.0f, 1.0f));
		    }
			if(boss >= 6 && boss < 9){
				model = glm::rotate(model, glm::radians(25.0f), glm::vec3(0.0f, 0.0f, 1.0f));
			}
			if(boss > 2 && boss < 9){
				model = glm::scale(model, boss_scales[3]);
			}
			else if(boss >= 9 && boss < 15)
			{
				if(boss > 11 && bossDefeated){
					model = glm::rotate(model, glm::radians(bossRotate + legVariations[(boss - 9)]), glm::vec3(0.0f, 0.0f, 1.0f));
				}else if(boss < 12 && bossDefeated){
					model = glm::rotate(model, glm::radians(-bossRotate + legVariations[(boss - 9)]), glm::vec3(0.0f, 0.0f, 1.0f));
				}				
				model = glm::scale(model, boss_scales[4]);
			}
			else if(boss == 15)
			{
				model = glm::scale(model, boss_scales[5]);
			}
			else
			{
				model = glm::scale(model, boss_scales[boss]);
			}

			lighting_shader.setFloat("material.shininess", 50.0f);
			lighting_shader.setMat4("model", model);

			glDrawArrays(GL_TRIANGLES, 0, 36);
		}


		//Armour suit
		glm::vec3 suit_scales[] = {
			glm::vec3( 0.2f,  0.2f,  0.2f),  //Head
			glm::vec3( 0.4f,  0.6f,  0.2f),  //Body
			glm::vec3( 0.15f,  0.65f,  0.1f),   //Leg 1
			glm::vec3( 0.15f,  0.65f,  0.1f),   //Leg 2
			glm::vec3( 0.15f,  0.6f,  0.1f),   //Arm 1
			glm::vec3( 0.15f,  0.6f,  0.1f),   //Arm 2
		};
		glm::vec3 suit_positions[] = {
			glm::vec3( 0.0f, 1.5f,  0.0f),   //Head
			glm::vec3( 0.0f, 1.1f,  0.0f),     //Body
			glm::vec3( -0.1f, 0.5f, 0.0f),   //Leg 1
			glm::vec3( 0.1f, 0.5f,  0.0f),  //Leg 2
			glm::vec3( 0.25f, 1.0f,  0.0f),   	//Arm 1
			glm::vec3( -0.25f, 1.0f, 0.0f),   //Arm 2
		};

		glBindVertexArray(VAO_box);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_metal_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_metal_specular);
		glm::vec3 multi_suits[] = {
			glm::vec3(1.0f, -3.8f, -33.5f),   //Head
			glm::vec3(-9.5f, -3.8f, -33.5f),     //Body
			glm::vec3(1.0f, -3.8f, -28.5f),   //Leg 1
			glm::vec3(-9.5f, -3.8f, -28.5f),  //Leg 2

		};
		for(int suitNum = 0; suitNum < 4; suitNum++)
		{
			for(int suit = 0; suit < 6; suit++)
			{	
				model = glm::mat4();
				model = glm::translate(model, glm::vec3(multi_suits[suitNum]));
				model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
				model = glm::translate(model, suit_positions[suit]);
				model = glm::scale(model, suit_scales[suit]);
				lighting_shader.setFloat("material.shininess", 10.0f);
				lighting_shader.setMat4("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}
		}	
			
		//Table
		unsigned int table_textures[] = {
			tex_tabletop,
			tex_tableside,
			tex_tableside,
			tex_tableside,
			tex_tableside,
			tex_wood
		};
		glm::vec3 table_scales[] = {
			glm::vec3( 2.5f,  0.02f,  1.0f),  //Top
			glm::vec3( 2.5f,  0.25f,  0.02f),  //Long Side 1
			glm::vec3( 2.5f,  0.25f,  0.02f),  //Long Side 2
			glm::vec3( 0.02f,  0.25f,  1.0f), //Short Side 1
			glm::vec3( 0.02f,  0.25f,  1.0f),  //Short Side 2
			glm::vec3( 2.0f,  0.75f,  0.8f)  //Wooden Block base
		};
		glm::vec3 table_positions[] = {
		    glm::vec3( 0.0f,  0.76f,  0.0f),  //Top
			glm::vec3( 0.0f,  0.65f,  -0.5f),  //Long Side 1
			glm::vec3( 0.0f,  0.65f,  0.5f),  //Long Side 2
			glm::vec3( 1.25f,  0.65f,  0.0f), //Short Side 1
			glm::vec3( -1.25f,  0.65f,  0.0f),  //Short Side 2
			glm::vec3( 0.0f,  0.375f,  0.0f)  //Wooden Block base
		};
		glBindVertexArray(VAO_box);

		for(int tabl = 0; tabl < 6; tabl++)
		{
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, table_textures[tabl]);
			glActiveTexture(GL_TEXTURE1);
			if(tabl == 5)
			{
				glBindTexture(GL_TEXTURE_2D, tex_dark_texture);
			}else{
				glBindTexture(GL_TEXTURE_2D, tex_marble_specular);
			}
			
			model = glm::mat4();
			model = glm::translate(model, glm::vec3( -4.0f, -3.5f ,-29.0f));
			model = glm::translate(model, table_positions[tabl]);
			model = glm::scale(model, table_scales[tabl]);
			lighting_shader.setFloat("material.shininess", 50.0f);
			lighting_shader.setMat4("model", model);
			glDrawArrays(GL_TRIANGLES, 0, 36);

		}

		//Chair
		unsigned int chair_textures[] = {
			tex_chair_specular,
			tex_wood
		};
		glm::vec3 chair_scales[] = {
			glm::vec3( 0.5f,  0.05f, 0.5f),	//Seat
			glm::vec3( 0.5f,  0.05f,  0.5f),  //Seat base
			glm::vec3( 0.5f,  0.5f,  0.05f),  //Seat backrest
			glm::vec3( 0.1f,  0.65f,  0.1f)  //Seat legs
		};
		glm::vec3 chair_positions[] = {
			glm::vec3( 0.0f,  0.6f,  0.0f), //Seat
			glm::vec3( 0.0f,  0.55f,  0.0f),  //Seat base
			glm::vec3( 0.0f,  0.785f,  -0.275f),  //Seat backrest
			glm::vec3( 0.175f,  0.25f,  -0.175f),  //Seat leg1
			glm::vec3( -0.175f,  0.25f,  -0.175f),  //Seat leg2
			glm::vec3( 0.175f,  0.25f,  0.175f),  //Seat leg3
			glm::vec3( -0.175f,  0.25f,  0.175f),  //Seat leg4
		};
		glBindVertexArray(VAO_box);
		for(int chair = 0; chair < 7; chair++)
		{	
			if (chair == 0)
			{
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, chair_textures[0]);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_fabric_specular);
			}else{
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, chair_textures[1]);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_dark_texture);
			}
		
			model = glm::mat4();
			model = glm::translate(model, glm::vec3( -8.15f,-3.5f ,-22.5f));
			model = glm::rotate(model, glm::radians(140.0f), glm::vec3(0.0f, 1.0f, 0.0f));

			model = glm::translate(model, chair_positions[chair]);
			if(chair > 2)
			{
				model = glm::scale(model, chair_scales[3]);
			}else{
				model = glm::scale(model, chair_scales[chair]);
			}
			lighting_shader.setFloat("material.shininess", 15.0f);
			lighting_shader.setMat4("model", model);
			glDrawArrays(GL_TRIANGLES, 0, 36);

		}

		//Gold coin tub
		unsigned int coin_textures[] = {
			tex_goldcoins,
			tex_wood
		};
		glm::vec3 coin_scales[] = {
			glm::vec3( 2.9f,  0.02f, 2.3f),	//Coins
			glm::vec3( 3.0f,  1.0f,  2.4f)  //Wooden Block base
		};
		glm::vec3 coin_positions[] = {
			glm::vec3( 0.0f,  1.0f,  0.0f),
			glm::vec3( 0.0f,  0.5f,  0.0f)  //Wooden Block base
		};
		glBindVertexArray(VAO_box);

		for(int coin = 0; coin < 2; coin++)
		{	
			if (coin == 0)
			{
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, coin_textures[0]);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_metal_specular);
			}else{
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, coin_textures[1]);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_dark_texture);
			}
			

			model = glm::mat4();
			model = glm::translate(model, glm::vec3( -4.0f, -3.5f ,-34.0f));
			model = glm::translate(model, coin_positions[coin]);
			model = glm::scale(model, coin_scales[coin]);
			lighting_shader.setFloat("material.shininess", 15.0f);
			lighting_shader.setMat4("model", model);
			glDrawArrays(GL_TRIANGLES, 0, 36);

		}

		//Book
		unsigned int book_textures[] = {
			tex_bookcover,
			tex_bookcover,
			tex_bookside,
			tex_bookpages
		};
		glm::vec3 book_scales[] = {
			glm::vec3( 0.2f,  0.02f,  0.3f),  //Cover
			glm::vec3( 0.2f,  0.02f,  0.3f),  //Cover
			glm::vec3( 0.007f,  0.085f,  0.3f),  //Side
			glm::vec3( 0.26f,  0.05f,  0.18f)  //Pages
		
		};
		glm::vec3 book_positions[] = {
		    glm::vec3( 0.0f,  0.0f,  0.0f),  //Cover 1
			glm::vec3( 0.0f,  0.065f,  0.0f),  //Cover 2
			glm::vec3( -0.1035f,  0.035f,  0.0f),  //Side
			glm::vec3( 0.f,  0.04f,  -0.002f)  //Pages
		};
		float multiBooks[] = {
			0.0f,
			15.0f,
			-30.0f,
			45.0f,
			-60.0f
		};
		//1st Stack
		glBindVertexArray(VAO_box);
		for(int bookNum = 0; bookNum < 5; bookNum++){
			for(int book = 0; book < 4; book++)
			{	
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, book_textures[book]);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_book_specular);
				model = glm::mat4();
				model = glm::translate(model, glm::vec3( -8.5f,(-3.5f + (0.085*bookNum)),-23.5f));
				if (book == 3)
				{
					model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
				}
				model = glm::rotate(model, glm::radians(multiBooks[bookNum]), glm::vec3(0.0f, 1.0f, 0.0f));
				model = glm::translate(model, book_positions[book]);
				model = glm::scale(model, book_scales[book]);
					
				lighting_shader.setFloat("material.shininess", 50.0f);
				lighting_shader.setMat4("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}
		}	
		//2nd Stack
		for(int bookNum = 0; bookNum < 3; bookNum++){
			for(int book = 0; book < 4; book++)
			{	
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, book_textures[book]);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_book_specular);
				model = glm::mat4();

				model = glm::translate(model, glm::vec3( -8.2f,(-3.5f + (0.085*bookNum)),-23.2f));
				if (book == 3)
				{
					model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
				}
				
				model = glm::rotate(model, glm::radians(multiBooks[bookNum]), glm::vec3(0.0f, 1.0f, 0.0f));
				
				model = glm::translate(model, book_positions[book]);
			
				model = glm::scale(model, book_scales[book]);
				
					
				lighting_shader.setFloat("material.shininess", 50.0f);
				lighting_shader.setMat4("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}
		}	


		//Carpet
		glBindVertexArray(VAO_box);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_carpet_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_carpet_specular);

		model = glm::mat4();
		model = glm::scale(model, glm::vec3(3.0f, 1.0f, 17.0f));
		model = glm::translate(model, glm::vec3(0.0f,-0.475f, -0.088f));
		lighting_shader.setFloat("material.shininess", 12.0f);
		lighting_shader.setMat4("model", model);
		glDrawArrays(GL_TRIANGLES, 0, 36);


		//Floor Main Room
		glBindVertexArray(VAO_box);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_floor_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_floor_specular);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, -0.01f, 0.0f));
		model = glm::scale(model, glm::vec3(20.0f, 0.001f, 20.0f));
		lighting_shader.setFloat("material.shininess", 10.5f);
		lighting_shader.setMat4("model", model);

		glDrawArrays(GL_TRIANGLES, 0, 36);
		
		//Roof Main Room
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, 10.01f, 0.0f));
		model = glm::scale(model, glm::vec3(20.0f, 0.001f, 20.0f));
		lighting_shader.setFloat("material.shininess", 1000.5f);
		lighting_shader.setMat4("model", model);

		glDrawArrays(GL_TRIANGLES, 0, 36);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, 10.01f, -20.0f));
		model = glm::scale(model, glm::vec3(20.0f, 0.001f, 20.0f));
		lighting_shader.setFloat("material.shininess", 1000.5f);
		lighting_shader.setMat4("model", model);

		glDrawArrays(GL_TRIANGLES, 0, 36);

		//Main room walls
		glm::vec3 wall_scales[] = {
			glm::vec3( 10.0f,  20.6f,  0.02f),  //Back
			glm::vec3( 10.0f,  8.9f,  0.02f),	//Front  Left
			glm::vec3( 10.0f,  8.9f,  0.02f),   //Front Right
			glm::vec3( 0.02f,  15.0f,  6.0f),    //Stair Wall Right
			glm::vec3( 0.02f,  15.0f,  6.0f),    //Stair Wall Left
			glm::vec3( 0.02f,  10.0f,  20.6f),	//Left
			glm::vec3( 0.02f,  10.0f,  20.6f),	//Right
			glm::vec3( 3.0f, 10.5f, 0.01f)       // Behind throne
		};
		glm::vec3 wall_positions[] = {
			glm::vec3( 10.3f, 5.0f,  10.0f),   //Back
			glm::vec3( -1.5f, 4.95f, -10.0f),   //Front Left
			glm::vec3( 10.4f, 4.95f, -10.0f),   //Front Right
			glm::vec3( 1.5f,  -5.0f, -13.0f),   //Stair Wall Right
			glm::vec3(-1.5f,  -5.0f, -13.0f),   //Stair Wall Right
			glm::vec3(-10.0f, 0.0f,   0.0f),   //Left
			glm::vec3( 10.0f, 0.0f,   0.0f),   //Right
			glm::vec3(0.0f, -0.55f, -16.0f)     //Behind throne
		};
		glBindVertexArray(VAO_box);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_wall_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_wall_specular);

		for(int wall = 0; wall < 8; wall++)
		{	
			model = glm::mat4();
			
			model = glm::translate(model, wall_positions[wall]);
			if(wall < 3)
			{
				model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 0.0f, 1.0f));
			}
			
			model = glm::scale(model, wall_scales[wall]);

			model = glm::translate(model, glm::vec3(0.0f, 0.5f, 0.0f));
			
			lighting_shader.setFloat("material.shininess", 150.0f);
			lighting_shader.setMat4("model", model);

			glDrawArrays(GL_TRIANGLES, 0, 36);
		}
		lighting_shader.setFloat("material.shininess", 15.0f);
		
		//Throne
		glBindVertexArray(VAO_box);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_throne_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_throne_specular);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, 6.55f, -15.95f));
		model = glm::scale(model, glm::vec3(3.0f, 7.0f, 0.01f));
		lighting_shader.setFloat("material.shininess", 50.5f);
		lighting_shader.setMat4("model", model);

		glDrawArrays(GL_TRIANGLES, 0, 36);


		//Stairs
		glm::vec3 stair_scales = glm::vec3(3.0f,  0.5f,  1.0f);
		glm::vec3 stair_positions[] = {
			glm::vec3( 0.0f,  0.25f,  -10.5f),
			glm::vec3( 0.0f,  0.75f,  -11.5f), 
			glm::vec3( 0.0f,  1.25f,  -12.5f), 
			glm::vec3( 0.0f,  1.75f,  -13.5f), 
			glm::vec3( 0.0f,  2.25f,  -14.5f), 
			glm::vec3( 0.0f,  2.75f,  -15.5f),    
	  
		};
		glBindVertexArray(VAO_box);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_carpet_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_fabric_specular);

		if(STAIRS_ACTIVATED == true)
		{
			stair_translate += 0.01f;
			if(stair_translate > 2.0f){
				stair_translate = 2.0f;
				STAIRS_ACTIVATED = false;
				camera.OpenStairs();
			} 
		}

		for(int stair = 0; stair < 6; stair++)
		{
			model = glm::mat4();
			model = glm::translate(model, stair_positions[stair]);
			model = glm::scale(model, stair_scales);
			float translateModifier = (float((stair+1)*stair_translate) * -1.0f);
			model = glm::translate(model, glm::vec3(0.0f, translateModifier, 0.0f));
			lighting_shader.setFloat("material.shininess", 15.0f);
			lighting_shader.setMat4("model", model);

			glDrawArrays(GL_TRIANGLES, 0, 36);
		}

		//Teleport Crystal
		glm::vec3 teleport_scales[] = {
			glm::vec3( 0.3f,  0.3f,  0.3f),  //Crystal
			glm::vec3( 0.5f,  1.0f,  0.5f),  //Pillar

		};
		glm::vec3 teleport_positions[] = {
			glm::vec3( 0.0f,  1.3f, 0.0f),   //Crystal
			glm::vec3(0.0f,  0.5f, 0.0f),    //Pillar
		};
		unsigned int teleport_textures[] = {
			tex_crytal_diffuse,
			tex_marble_diffuse
		};
		unsigned int teleport_specular[] = {
			tex_metal_specular,
			tex_marble_specular
		};
		glBindVertexArray(VAO_box);
		
		for(int tele = 0; tele < 2; tele++)
		{	
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, teleport_textures[tele]);
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, teleport_specular[tele]);
			model = glm::mat4();
			model = glm::translate(model, glm::vec3(7.5f, -3.3f, -23.5f));
			model = glm::translate(model, teleport_positions[tele]);
			model = glm::scale(model, teleport_scales[tele]);
			if(tele == 0)
			{
				crystal_rotate += 1.0f;
				if(abs(crystal_rotate - 360.0f) <= 0.1f) crystal_rotate = 0.0f;
				model = glm::rotate(model, glm::radians(crystal_rotate), glm::vec3(0.0f, 1.0f, 1.0f));

			}
			lighting_shader.setFloat("material.shininess", 15.0f);
			lighting_shader.setMat4("model", model);

			glDrawArrays(GL_TRIANGLES, 0, 36);
		}

		//Floor Treasure Room
		glBindVertexArray(VAO_box);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, tex_floor_diffuse);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, tex_floor_specular);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, -3.5f, -26.0f));
		model = glm::scale(model, glm::vec3(20.0f, 0.001f, 20.0f));
		lighting_shader.setFloat("material.shininess", 10.5f);
		lighting_shader.setMat4("model", model);

		glDrawArrays(GL_TRIANGLES, 0, 36);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, 0.75f, -26.0f));
		model = glm::scale(model, glm::vec3(20.0f, 0.001f, 20.0f));
		lighting_shader.setFloat("material.shininess", 10.5f);
		lighting_shader.setMat4("model", model);

		glDrawArrays(GL_TRIANGLES, 0, 36);

		//Treasue Room Walls
		glm::vec3 treasureW_scales[] = {
			glm::vec3( 0.02f,  5.0f,  5.0f),  
			glm::vec3( 0.02f,  5.0f,  5.0f),
			glm::vec3( 4.25f,  5.0f,  0.02f),
			glm::vec3( 5.75f,  5.0f,  0.02f),
		};
		glm::vec3 trasureP_positions[] = {
			glm::vec3( 1.5f,  -1.5f, -18.5f),   
			glm::vec3(-1.5f,  -1.5f, -18.5f),
			glm::vec3( 10.0f,  -1.5f, -23.5f),
			glm::vec3( 1.5f,  -1.5f, -28.5f),
			glm::vec3( 1.5f,  -1.5f, -33.5f),
			glm::vec3(-10.0f,  -1.5f, -28.5f),
			glm::vec3(-10.0f,  -1.5f, -33.5f), 
			glm::vec3(-10.0f,  -1.5f, -23.5f),
			glm::vec3(-7.875f,  -1.5f, -21.0f),
			glm::vec3(-3.625f,  -1.5f, -21.0f),
			glm::vec3(7.875f,  -1.5f, -21.0f),
			glm::vec3(3.625f,  -1.5f, -21.0f),
			glm::vec3(3.625f,  -1.5f, -26.0f),
			glm::vec3(7.875f,  -1.5f, -26.0f),
			glm::vec3(-1.375f,  -1.5f, -36.0f),
			glm::vec3(-7.125f,  -1.5f, -36.0f),
		};
		glBindVertexArray(VAO_box);
		

		for(int troom = 0; troom < 16; troom++)
		{	
			
			if(troom < 7 || troom > 13)
			{
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, tex_swordwall_diffuse);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_dark_texture);
			}
			else
			{
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, tex_bookwall_diffuse);
				glActiveTexture(GL_TEXTURE1);
				glBindTexture(GL_TEXTURE_2D, tex_dark_texture);
			}

			model = glm::mat4();
			model = glm::translate(model, trasureP_positions[troom]);
			if(troom < 8)
			{
				model = glm::rotate(model, glm::radians(90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
			}
			if(troom < 2)
			{
				model = glm::scale(model, treasureW_scales[0]);
			}
			else if (troom >= 2 && troom < 8)
			{
				model = glm::scale(model, treasureW_scales[1]);
			}
			else if (troom >= 8 && troom < 14){
				model = glm::scale(model, treasureW_scales[2]);
			}
			else
			{
				model = glm::scale(model, treasureW_scales[3]);
			}
			
			lighting_shader.setFloat("material.shininess", 150.0f);
			lighting_shader.setMat4("model", model);

			glDrawArrays(GL_TRIANGLES, 0, 36);
		}
		lighting_shader.setFloat("material.shininess", 15.0f);
		// Draw the light source
		lamp_shader.use();
		lamp_shader.setMat4("projection", projection);
		lamp_shader.setMat4("view", view);
		model = glm::mat4();
		if(DROPPED_LIGHT == true)
			model = glm::translate(model, light_pos);
		else
			model = glm::translate(model, camera.Position);
		model = glm::scale(model, glm::vec3(0.01f)); // a smaller cube
		lamp_shader.setMat4("model", model);

		
		if(STAIRS_ACTIVATED == true) lamp_shader.setFloat("intensity", 1.0);
		else lamp_shader.setFloat("intensity", 0.15);

		glBindVertexArray(VAO_light);
		glDrawArrays(GL_TRIANGLES, 0, 36);

		// glfw: swap buffers and poll IO events (keys pressed/released, mouse moved etc.)
		// -------------------------------------------------------------------------------
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	// optional: de-allocate all resources once they've outlived their purpose:
	// ------------------------------------------------------------------------
	glDeleteVertexArrays(1, &VAO_box);
	glDeleteBuffers(1, &VBO_box);

	// glfw: terminate, clearing all previously allocated GLFW resources.
	// ------------------------------------------------------------------
	glfwTerminate();
	return 0;
}



// process all input: query GLFW whether relevant keys are pressed/released this frame and react accordingly
// ---------------------------------------------------------------------------------------------------------
void process_input(GLFWwindow *window)
	{
    	if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        	glfwSetWindowShouldClose(window, true);

	glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_RELEASE)
		sprint = false;
	else
		sprint = true;	// double speed with "Shift" pressed

	//Movement
	if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
		camera.ProcessKeyboard(FORWARD, delta_time, sprint);
	if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
		camera.ProcessKeyboard(BACKWARD, delta_time, sprint);
	if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
		camera.ProcessKeyboard(LEFT, delta_time, sprint);
	if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
		camera.ProcessKeyboard(RIGHT, delta_time, sprint);
	//Drop and pickup camera
	if (glfwGetKey(window, GLFW_KEY_F) == GLFW_PRESS && ACTION_DELAY == 0)
	{
		ACTION_DELAY = 60;
		if(DROPPED_LIGHT == false)
		{
			light_pos = camera.Position; 		
			DROPPED_LIGHT = true;
		}
		else
		{
			DROPPED_LIGHT = false;
		}
	}
	//Brightness up
	if (glfwGetKey(window, GLFW_KEY_L) == GLFW_PRESS && ACTION_DELAY == 0)
	{
		ACTION_DELAY = 5;
		UP_BRIGHTNESS = true;
	}
	//Brightness down
	if (glfwGetKey(window, GLFW_KEY_K) == GLFW_PRESS && ACTION_DELAY == 0)
	{
		ACTION_DELAY = 5;
		DOWN_BRIGHTNESS = true;
	}
	//Toggle projection
	if (glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS && ACTION_DELAY == 0)
	{
		ACTION_DELAY = 60;
		if(PERSPECTIVE_ON)
			PERSPECTIVE_ON = false;
		else	
			PERSPECTIVE_ON = true;
	
	}
	//Toggle brightness
	if (glfwGetKey(window, GLFW_KEY_O) == GLFW_PRESS && ACTION_DELAY == 0)
	{
		ACTION_DELAY = 60;
		if(DARK_SCENE_ON)
			DARK_SCENE_ON = false;
		else	
			DARK_SCENE_ON = true;
	
	}
	//toggle red button
	if (glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS && NEAR_STAIRS && bossDefeated)
	{

		if(STAIRS_ACTIVATED == false) 		
			STAIRS_ACTIVATED = true;
		
	}
}


// glfw: whenever the window size changed (by OS or user resize) this callback function executes
// ---------------------------------------------------------------------------------------------
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
	// make sure the viewport matches the new window dimensions; note that width and 
	// height will be significantly larger than specified on retina displays.
	glViewport(0, 0, width, height);
}

unsigned int loadTexture(char const * path)
{
	unsigned int textureID;
	glGenTextures(1, &textureID);

	int width, height, nrComponents;
	stbi_set_flip_vertically_on_load(true); // tell stb_image.h to flip loaded texture's on the y-axis.
	unsigned char *data = stbi_load(path, &width, &height, &nrComponents, 0);
	if (data)
	{
		GLenum format;
		if (nrComponents == 1)
			format = GL_RED;
		else if (nrComponents == 3)
			format = GL_RGB;
		else if (nrComponents == 4)
			format = GL_RGBA;

		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
		glGenerateMipmap(GL_TEXTURE_2D);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		stbi_image_free(data);
	}
	else
	{
		std::cout << "Texture failed to load at path: " << path << std::endl;
		stbi_image_free(data);
	}

	return textureID;
}


void mouseCallback(GLFWwindow* window, double xpos, double ypos)
{
	mousex = xpos;
	if (firstMouse)
    {
        lastX = xpos;
        lastY = ypos;
        firstMouse = false;
    }

    float xoffset = xpos - lastX;
    float yoffset = lastY - ypos; // reversed since y-coordinates go from bottom to top

    lastX = xpos;
    lastY = ypos;

    camera.ProcessMouseMovement(xoffset, yoffset);
}