#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform sampler2D uTexture1;
uniform sampler2D uTexture2;
uniform sampler2D uTexture3;

varying vec2 vTextureCoord;

void main(void) {

	vec4 color1 = texture2D(uTexture1, vTextureCoord);
	vec4 color2 = texture2D(uTexture2, vTextureCoord);
	vec4 color3 = texture2D(uTexture3, vTextureCoord);
	
	float ratio =  0.299 * color3.r + 0.587 * color3.g + 0.114 * color3.b;
	
	gl_FragColor = mix(color1, color2, ratio);
}