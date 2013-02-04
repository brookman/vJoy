#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform float uRed;
uniform float uGreen;
uniform float uBlue;
varying vec2 vTextureCoord;

void main(void) {
	gl_FragColor = vec4(uRed, uGreen, uBlue, 1.0);
	
}