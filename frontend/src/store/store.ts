import { createStore, Store } from 'vuex';
import axios, { AxiosResponse } from 'axios';

interface State {
  isLoggedIn: boolean;
  roles: string[] | null;
  accessToken: string | null;
  email: string | null;
  userId: number | null;
}

interface ResponseSignin {
  id: number | null, 
  email: string | null, 
  roles: string[] | null, 
  accessToken: string | null, 
  tokenType: string | null
}

const store: Store<State> = createStore({
  state(): State {
    return {
      isLoggedIn: false,
      roles: [],
      accessToken: null,
      email: null,
      userId: null
    };
  },
  mutations: {
    SET_LOGIN_STATE(state: State, isLoggedIn: boolean) {
      state.isLoggedIn = isLoggedIn;
    },
    SET_ROLE(state: State, roles: string[] | null) {
      state.roles = roles;
    },
    SET_ACCESS_TOKEN(state: State, accessToken: string | null) {
      state.accessToken = accessToken;
    },
    SET_EMAIL(state: State, email: string | null) {
      state.email = email;
    },
    SET_USERID(state: State, id: number | null) {
      state.userId = id;
    }
  },
  actions: {
    async login({ commit }, userData: { email: string; password: string }): Promise<{ success: boolean; error?: string }> {
      try {
        const response = await axios.post<ResponseSignin>('http://localhost:8080/course-management/signin', userData);
        
        console.log("Dữ liệu tại login: ", response.data)
        console.log("Kiểu dữ liệu của response: ", typeof response)
              
        if (response.data) {
          commit('SET_LOGIN_STATE', true);
          commit('SET_ROLE', response.data.roles || null);
          commit('SET_ACCESS_TOKEN', response.data.accessToken || null);
          commit('SET_EMAIL', response.data.email || null);
          commit('SET_USERID', response.data.id || null);

          console.log("State sau khi đăng nhập:", store.state);
          console.log("Roles sau khi đăng nhập:", store.state.roles);

          return { success: true }
        } else {
          // Xử lý trường hợp không nhận được dữ liệu từ API
          console.error('Không nhận được dữ liệu từ API khi đăng nhập.');
          return { success: false, error: 'Không nhận được dữ liệu từ API khi đăng nhập.' };
        }
      } catch (error) {
        console.error('Lỗi khi đăng nhập:', error);
        return { success: false, error: 'Đã có lỗi xảy ra khi đăng nhập.' };
      }
    },
    logout({ commit }): void {
      commit('SET_LOGIN_STATE', false);
      commit('SET_ROLE', null);
      commit('SET_ACCESS_TOKEN', null);
    },
  },
  getters: {
    getRoles(state) {
      return state.roles;
    },
    getAccessToken(state) {
      return state.accessToken;
    }
  }
});

export default store;