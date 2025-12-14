import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(), // Manejo global de errores
    provideRouter(routes), // Configuración de rutas
    provideZoneChangeDetection({eventCoalescing: true}), // Detección de cambios optimizada
    importProvidersFrom(FormsModule), // Importación de FormsModule para formularios
    provideHttpClient() // Proveedor de HttpClient para solicitudes HTTP
  ]
};
