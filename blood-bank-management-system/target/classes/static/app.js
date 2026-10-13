const API = "/api";
const $ = (id) => document.getElementById(id);
const bloodGroups = ["A+","A-","B+","B-","AB+","AB-","O+","O-"];

async function api(path, options = {}) {
  const response = await fetch(API + path, {
    ...options,
    headers: { "Content-Type": "application/json", ...(options.headers || {}) }
  });
  if (response.status === 401) throw new Error("Authentication required. Use your staff/admin account when the browser prompts.");
  if (response.status === 403) throw new Error("Your account does not have permission for this action.");
  if (!response.ok) {
    let message = `Request failed (${response.status})`;
    try { const body = await response.json(); message = body.message || message; } catch {}
    throw new Error(message);
  }
  if (response.status === 204) return null;
  return response.json();
}
function message(id, text, ok) {
  const node = $(id); node.textContent = text; node.className = "form-message " + (ok ? "success" : "error");
}
function escapeHtml(value) {
  return String(value ?? "").replace(/[&<>"']/g, c => ({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
}
async function loadInventory() {
  const data = await api("/inventory");
  const byGroup = Object.fromEntries(data.map(x => [x.bloodGroup, x.units]));
  $("inventory").innerHTML = bloodGroups.map(g => `<div class="blood-card"><b>${g}</b><strong>${byGroup[g] ?? 0}</strong><small>units recorded</small></div>`).join("");
}
async function loadDonors() {
  const data = await api("/donors");
  $("donorCount").textContent = data.length;
  $("donorRows").innerHTML = data.length ? data.slice().reverse().slice(0,8).map(d =>
    `<tr><td>${escapeHtml(d.fullName)}</td><td><b>${escapeHtml(d.bloodGroup)}</b></td><td>${escapeHtml(d.city)}</td><td>${escapeHtml(d.phone)}</td></tr>`
  ).join("") : `<tr><td colspan="4" class="muted">No donors registered yet.</td></tr>`;
}
async function loadRequests() {
  const data = await api("/requests");
  $("requestCount").textContent = data.filter(r => r.status === "PENDING").length;
  $("requestRows").innerHTML = data.length ? data.slice().reverse().slice(0,8).map(r =>
    `<tr><td>${escapeHtml(r.hospitalName)}</td><td><b>${escapeHtml(r.bloodGroup)}</b></td><td>${escapeHtml(r.units)}</td><td><span class="pill">${escapeHtml(r.status)}</span></td><td>${r.createdAt ? new Date(r.createdAt).toLocaleDateString() : "—"}</td></tr>`
  ).join("") : `<tr><td colspan="5" class="muted">No requests submitted yet.</td></tr>`;
}
async function refresh() {
  try { await Promise.all([loadInventory(), loadDonors(), loadRequests()]); }
  catch (e) {
    $("inventory").innerHTML = `<p class="muted">${escapeHtml(e.message)}</p>`;
    $("donorRows").innerHTML = `<tr><td colspan="4" class="muted">${escapeHtml(e.message)}</td></tr>`;
    $("requestRows").innerHTML = `<tr><td colspan="5" class="muted">${escapeHtml(e.message)}</td></tr>`;
  }
}
$("refreshBtn").addEventListener("click", refresh);
$("donorForm").addEventListener("submit", async event => {
  event.preventDefault();
  const form = event.currentTarget;
  const data = Object.fromEntries(new FormData(form).entries());
  try {
    await api("/donors", { method: "POST", body: JSON.stringify(data) });
    form.reset(); message("donorMessage", "Donor registered successfully.", true); await refresh();
  } catch (e) { message("donorMessage", e.message, false); }
});
$("requestForm").addEventListener("submit", async event => {
  event.preventDefault();
  const form = event.currentTarget;
  const data = Object.fromEntries(new FormData(form).entries());
  data.units = Number(data.units);
  try {
    await api("/requests", { method: "POST", body: JSON.stringify(data) });
    form.reset(); message("requestMessage", "Request submitted for staff verification.", true); await refresh();
  } catch (e) { message("requestMessage", e.message, false); }
});
refresh();
