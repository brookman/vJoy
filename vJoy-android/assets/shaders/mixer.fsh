#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform sampler2D uTexture1;
uniform sampler2D uTexture2;

uniform float uFactor;

varying vec2 vTextureCoord;

void main(void) {

	vec4 color1 = texture2D(uTexture1, vTextureCoord);
	vec4 color2 = texture2D(uTexture2, vTextureCoord);
	
	gl_FragColor = mix(color2, color1, uFactor);
}