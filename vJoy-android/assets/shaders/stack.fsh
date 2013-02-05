#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform sampler2D uTexture1;
uniform sampler2D uTexture2;

varying vec2 vTextureCoord;

void main(void) {

	vec4 color1 = texture2D(uTexture1, vTextureCoord);
	vec4 color2 = texture2D(uTexture2, vTextureCoord);
	vec3 result = color1.rgb * (1.0 - color2.a) + color2.rgb * color2.a;
	
	gl_FragColor = vec4(result, min(color1.a + color2.a, 1.0));
}