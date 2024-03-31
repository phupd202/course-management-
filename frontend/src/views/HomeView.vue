<template>
  <header>
    <HomeSidebar></HomeSidebar>
    <HomeHeader></HomeHeader>
  </header>

  <main style="margin-top: 58px;">
      <div class="container pt-4">
        <table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First</th>
      <th scope="col">Last</th>
      <th scope="col">Handle</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">1</th>
      <td>Mark</td>
      <td>Otto</td>
      <td>@mdo</td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>Jacob</td>
      <td>Thornton</td>
      <td>@fat</td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td>Larry</td>
      <td>the Bird</td>
      <td>@twitter</td>
    </tr>
  </tbody>
</table>



      </div>
  </main>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';

@Options({
  components: {
    HomeHeader,
    HomeSidebar,
  }
})

export default class HomeView extends Vue { }
</script>

<style>
* {
  margin: 0;
  padding: 0;
}

</style>
