import NotFoundError from './NotFoundError';

type Method = 'POST' | 'GET' | 'PUT' | 'DELETE' | 'PATCH' | 'HEAD';

type Config = {
  method: Method;
  headers?: Record<string, string>;
};

const isRedirectedToLoginPage = (response: Response) =>
  response.redirected && response.url === `${window.location.origin}/logg-inn`;

const fetch = (url: string, config: Config, body?: BodyInit): Promise<Response> =>
  window
    .fetch(url, {
      method: config.method,
      credentials: 'include',
      headers: config.headers,
      body: body,
    })
    .then((response: Response) => {
      if (isRedirectedToLoginPage(response)) {
        window.location.href = response.url; // Go to login page
      }
      if (!response.ok) {
        if (response.status === 404) {
          throw new NotFoundError();
        }
        if (response.status == 400) {
          throw new Error(response.statusText);
        }
        throw new Error('Response fra endepunkt var ikke ok');
      }
      return response;
    })
    .catch((error: Error) => {
      console.error(error);
      throw error;
    });

const head = (url: string, config?: Omit<Config, 'method'>): Promise<Response> =>
  fetch(url, { ...config, method: 'HEAD' });

const get = (url: string, config?: Omit<Config, 'method'>): Promise<Response> =>
  fetch(url, { ...config, method: 'GET' });

const getJson = <T>(url: string, config?: Omit<Config, 'method'>): Promise<T> =>
  get(url, {
    ...config,
    headers: { ...config?.headers, Accept: 'application/json' },
  }).then((response: Response) => response.json() as Promise<T>);

const post = <T>(
  url: string,
  config?: Omit<Config, 'method'>,
  body?: BodyInit
): Promise<Response> =>
  fetch(
    url,
    {
      ...config,
      method: 'POST',
    },
    body
  );

const postJson = <T>(url: string, config?: Omit<Config, 'method'>, body?: BodyInit): Promise<T> =>
  post(
    url,
    {
      ...config,
      headers: {
        ...config?.headers,
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    },
    body
  ).then((response: Response) => response.json() as Promise<T>);

const put = <T>(url: string, config?: Omit<Config, 'method'>, body?: BodyInit): Promise<Response> =>
  fetch(
    url,
    {
      ...config,
      method: 'PUT',
    },
    body
  );

const putJson = <T>(url: string, config?: Omit<Config, 'method'>, body?: BodyInit): Promise<T> =>
  put(
    url,
    {
      ...config,
      headers: {
        ...config?.headers,
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    },
    body
  ).then((response: Response) => response.json() as Promise<T>);

const patch = <T>(
  url: string,
  config: Omit<Config, 'method'>,
  body?: BodyInit
): Promise<Response> =>
  fetch(
    url,
    {
      ...config,
      method: 'PATCH',
    },
    body
  );

const patchJson = <T>(url: string, config?: Omit<Config, 'method'>, body?: BodyInit): Promise<T> =>
  patch(
    url,
    {
      ...config,
      headers: {
        ...config?.headers,
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    },
    body
  ).then((response: Response) => response.json() as Promise<T>);

const del = <T>(url: string, config?: Omit<Config, 'method'>): Promise<Response> =>
  fetch(url, {
    ...config,
    method: 'DELETE',
  });

export default {
  head,
  get,
  post,
  put,
  patch,
  del,
  patchJson,
  putJson,
  postJson,
  getJson,
};
