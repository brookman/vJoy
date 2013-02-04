#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D uTexture;
uniform float uH;
uniform float uS;
uniform float uV;
varying vec2 vTextureCoord;

vec3 hsv2rgb(vec3 hsv) {return mix(vec3(1.),clamp((abs(fract(hsv.x+vec3(3.,2.,1.)/3.)*6.-3.)-1.),0.,1.),hsv.y)*hsv.z;}

vec3 rgb2hsv(vec3 rgb) {
    vec3 hsv;
    hsv.z = max(rgb.r, max(rgb.g, rgb.b));
    float m = min(rgb.r, min(rgb.g, rgb.b));
    float c = hsv.z - m;
    if (c != 0.0) {
        hsv.y = c / hsv.z;
        vec3 delta = (hsv.z - rgb) / c;
        delta.rgb -= delta.brg;
        delta.rg += vec2(2.0,4.0);
        if (rgb.r >= hsv.z) {
            hsv.x = delta.b;
        } else if (rgb.g >= hsv.z) {
            hsv.x = delta.r;
        } else {
            hsv.x = delta.g;
		}
        hsv.x = fract(hsv.x / 6.0);
    }
    return hsv;
}

void main (void) {
    vec4 color = texture2D(uTexture, vTextureCoord);
	vec3 hsv = rgb2hsv(color.rgb);
	gl_FragColor = vec4(hsv2rgb(hsv + vec3(uH, uS * 2.0 - 1.0, uV * 2.0 - 1.0)), 1.0);
}