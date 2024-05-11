<template>
    <div class="swiper-slide">
      <img :src="props.imageSrc" alt="Slide Image">
      <div class="carousel-caption d-none d-md-block">
        <h3>{{ props.title }}</h3>
        <p>{{ props.description }}</p>
      </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps} from 'vue';

const props = defineProps<{
  imageSrc: string, 
  title: string, 
  description: string
}>();

</script>

<style>
.swiper-slide {
  text-align: center
}
</style>

