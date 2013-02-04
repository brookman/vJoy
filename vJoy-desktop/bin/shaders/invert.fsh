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
	gl_FragColor = vec4(vec3(1.0) - color.xyz, 1.0);
}