#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform sampler2D uTexture;
uniform float uRot;
uniform float uZoom;

varying vec2 vTextureCoord;

void main(void)
{
    vec2 p = vTextureCoord - 0.5;

	float rotation = uRot * 6.283185;
	mat2 rot = mat2(cos(rotation), -sin(rotation),
			   		sin(rotation),  cos(rotation));
	p = rot * p * uZoom;

    gl_FragColor = texture2D(uTexture, p + 0.5);
}