#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform sampler2D uTexture;
varying vec2 vTextureCoord;

void main(void) {
	vec4 color = texture2D(uTexture, vTextureCoord);
	float lum =  0.299 * color.r + 0.587 * color.g + 0.114 * color.b;
	gl_FragColor = vec4(lum, lum, lum, 1.0);
	
}