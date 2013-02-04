attribute vec4 a_position;
attribute vec2 a_texCoord;

varying vec2 vTextureCoord;
uniform vec2 resolution;

void main(void) {
	gl_Position = a_position;
	gl_Position.x /= resolution.x / resolution.y;
	vTextureCoord = a_texCoord;
}